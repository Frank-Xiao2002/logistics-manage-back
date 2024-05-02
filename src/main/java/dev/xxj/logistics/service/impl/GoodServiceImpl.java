package dev.xxj.logistics.service.impl;

import dev.xxj.logistics.model.*;
import dev.xxj.logistics.repo.GoodRepository;
import dev.xxj.logistics.repo.GoodStorageRepository;
import dev.xxj.logistics.service.GoodService;
import dev.xxj.logistics.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * GoodServiceImpl is the implementation of {@link GoodService}.
 *
 * @author Frank-Xiao
 */
@Service
@Slf4j
public class GoodServiceImpl implements GoodService {
    private final GoodRepository repository;
    private final GoodStorageRepository storageRepo;
    private final WarehouseService warehouseService;

    /**
     * Default Constructor of GoodServiceImpl.
     *
     * @param goodRepository    autowired {@link GoodRepository} bean
     * @param storageRepository autowired {@link GoodStorageRepository} bean
     * @param warehouseService  autowired {@link WarehouseService} bean
     */
    @Autowired
    public GoodServiceImpl(
            GoodRepository goodRepository,
            GoodStorageRepository storageRepository,
            WarehouseService warehouseService) {
        this.repository = goodRepository;
        this.storageRepo = storageRepository;
        this.warehouseService = warehouseService;
    }

    /**
     * Register a good into the system.
     * <p>
     * If the good is not registered, which is determined by its id field, it will be saved into the database.
     * Otherwise, it will return null to indicate that the good is already registered.
     * For the above situation, the user should use the {@link #updateGood(Good) updateGood} method to update the good.
     *
     * @param good the good object to be registered, which should not contain an id
     * @return the registered good object if the good is not registered, null otherwise
     */
    @Override
    @CachePut(value = "goods")
    public Good addGood(Good good) {
        return Objects.isNull(good.getId()) ? repository.save(good) : null;
    }

    /**
     * Get a good by its unique id. If the good does not exist, it will return null.
     *
     * @param id the id of the good
     * @return the {@link Good} object with the specified id if it exists, null otherwise
     */
    @Override
    @Cacheable(value = "goods")
    public Good getGoodById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Get goods by name.
     * <p>
     * Since the name of the good is not unique, it might return a list of goods with the same name.
     *
     * @param name the name of the good
     * @return a {@link List} of {@link Good} objects with the same name
     */
    @Override
    @Cacheable(value = "goods")
    public List<Good> getGoodByName(String name) {
        return repository.findByName(name);
    }

    /**
     * Update a good's details.
     * <p>
     * In order to prevent saving a brand-new good, the {@link Good} object should contain an id,
     * indicating that the good is already registered. If the good is registered, it will be updated,
     * or else it will return null. If the good is not registered, the user should use the
     * {@link #addGood(Good) addGood} method to register the good.
     *
     * @param good the good object to be updated, should contain the id of the good
     * @return the updated good object if the good is registered, null otherwise
     * @see Good
     */
    @SuppressWarnings("SpringCacheableMethodCallsInspection")
    @Override
    @CachePut(value = "goods")
    public Good updateGood(Good good) {
        return Objects.nonNull(good.getId()) && Objects.nonNull(getGoodById(good.getId())) ? repository.save(good) : null;
    }

    /**
     * Delete a good by its id.
     * <p>
     * Deletion is done in a cascade way. If the  good is deleted, all the related GoodStorage objects are deleted.
     *
     * @param id the id of the good to be deleted
     */
    @Override
    @CacheEvict(value = "goods")
    public void deleteGood(UUID id) {
        storageRepo.deleteAll(getGoodStorages(id));
        repository.deleteById(id);
    }

    /**
     * Get all registered goods.
     *
     * @return a {@link List} of all registered {@link Good} objects, no matter where they are stored
     * and how many they are
     */
    @Override
    @Cacheable(value = "goods")
    public List<Good> getAllGoods() {
        return repository.findAll();
    }

    /**
     * Get the locations of the specific good.
     *
     * @param id the id of the good
     * @return a {@link List} of {@link Warehouse} objects storing the good
     */
    @Override
    public List<Warehouse> getLocations(UUID id) {
        return storageRepo.findByGood_Id(id).stream()
                .map(GoodStorage::getWarehouse).toList();
    }

    /**
     * Get the total stock amount of the specified good.
     *
     * @param id the id of the good
     * @return the total stock amount of the specified good, in {@link Long} type
     */
    @Override
    public Long getTotalAmount(UUID id) {
        return storageRepo.findByGood_Id(id).stream()
                .map(GoodStorage::getAmount)
                .reduce(0L, Long::sum);
    }

    /**
     * Move a specific amount of a specific good from one warehouse to another.
     * <p>
     * If the warehouse does not have enough goods of this kind, it will return false to indicate
     * the failure of the operation. Otherwise, it will return true to indicate the success of the operation.
     *
     * @param moveDTO the MoveDTO object containing the goodId, fromId, toId, and amount
     * @return true if the operation is valid and successful, false otherwise
     */
    @Override
    public boolean moveGood(MoveDTO moveDTO) {
        UUID goodId = moveDTO.goodId(), fromId = moveDTO.fromId(), toId = moveDTO.toId();
        Long amount = moveDTO.amount();
        try {
            retrieveGood(new GoodStorageDto(fromId, goodId, amount));
            storeGood(new GoodStorageDto(toId, goodId, amount));
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Get all {@link GoodStorage} objects storing the specified good.
     *
     * @param goodId the id of the good
     * @return a list of GoodStorage objects storing the specified good
     */
    @Override
    public List<GoodStorage> getGoodStorages(UUID goodId) {
        return storageRepo.findByGood_Id(goodId);
    }

    /**
     * Store the goods in the warehouse.
     * <p>
     * If the warehouse is full, throw an exception.
     * If the GoodStorage exists, update the amount.
     * If the GoodStorage does not exist, validate the amount and try to create a new one.
     * <p>
     * This method is NOT thread-safe due to the lack of transaction management.
     *
     * @param goodStorageDto the GoodStorageDto object containing the goodId, warehouseId, and amount
     * @return the updated or brand new GoodStorage,
     * depending on whether the good is already stored in the given warehouse or not
     * @see GoodStorageDto
     */
    @Override
    public GoodStorage storeGood(GoodStorageDto goodStorageDto) {
        UUID goodId = goodStorageDto.goodId();
        UUID warehouseId = goodStorageDto.warehouseId();
        Long amount = goodStorageDto.amount();
        final GoodStorage[] saved = new GoodStorage[1];
        var maxAmount = warehouseService.getWarehouseById(warehouseId).getMaxAmount();
        var existAmount = warehouseService.getExistAmount(warehouseId);

        storageRepo.findByWarehouse_IdAndGood_Id(warehouseId, goodId)
                /* if the GoodStorage exists, try to update the amount*/
                .ifPresentOrElse(storage -> {
                    if (existAmount + amount > maxAmount) {
                        log.info("Warehouse is not full enough to store more goods of this type");
                        throw new IllegalArgumentException("Warehouse is full");
                    } else {
                        storage.setAmount(storage.getAmount() + amount);
                        saved[0] = storageRepo.save(storage);
                    }
                }, () -> {/* if the GoodStorage does not exist, try to create a new one*/
                    if (existAmount + amount > maxAmount) {
                        log.info("Warehouse is already full");
                        throw new IllegalArgumentException("Warehouse is full");
                    } else {
                        saved[0] = storageRepo.save(GoodStorage.builder()
                                .warehouse(warehouseService.getWarehouseById(warehouseId))
                                .amount(amount)
                                .good(repository.getReferenceById(goodId))
                                .build());
                    }
                });
        return saved[0];
    }

    /**
     * Retrieve a specific amount of a specific good from one warehouse.
     * <p>
     * If the warehouse does not have enough goods of this kind, throw an exception.
     * If the warehouse has enough goods, update the amount.
     * <p>
     * This method is NOT thread-safe due to the lack of transaction management.
     *
     * @param dto the GoodStorageDto object containing the goodId, warehouseId, and amount
     *            to be retrieved
     * @see GoodStorageDto
     */
    @Override
    public void retrieveGood(GoodStorageDto dto) {
        UUID goodId = dto.goodId(), warehouseId = dto.warehouseId();
        Long amount = dto.amount();
        storageRepo.findByWarehouse_IdAndGood_Id(warehouseId, goodId)
                .ifPresentOrElse(storage -> {
                    if (storage.getAmount() < amount) {
                        log.info("Not enough goods in the warehouse");
                        throw new IllegalArgumentException("Not enough goods in the warehouse");
                    }
                    storage.setAmount(storage.getAmount() - amount);
                    storageRepo.save(storage);
                    log.info("Goods {} retrieved successfully", goodId);
                }, () -> {
                    log.info("No such goods in the warehouse");
                    throw new IllegalArgumentException("No such goods in the warehouse");
                });
    }


}
