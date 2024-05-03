package dev.xxj.logistics.service.impl;

import dev.xxj.logistics.model.GoodStorage;
import dev.xxj.logistics.model.Warehouse;
import dev.xxj.logistics.repo.GoodStorageRepository;
import dev.xxj.logistics.repo.WarehouseRepository;
import dev.xxj.logistics.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * WarehouseServiceImpl is the implementation of {@link WarehouseService}.
 * It provides the basic CRUD operations for warehouses and the operations for checking the storage status.
 *
 * @author Frank-Xiao
 * @see WarehouseService
 * @see Warehouse
 */
@Service
@CacheConfig(cacheNames = "warehouse")
public class WarehouseServiceImpl implements WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final GoodStorageRepository storageRepo;

    @Autowired
    public WarehouseServiceImpl(WarehouseRepository warehouseRepository,
                                GoodStorageRepository storageRepo) {
        this.warehouseRepository = warehouseRepository;
        this.storageRepo = storageRepo;
    }

    @Override
    @CachePut()
    public Warehouse addWarehouse(Warehouse warehouse) {
        return Objects.isNull(warehouse.getId()) ? warehouseRepository.save(warehouse) : null;
    }

    @Cacheable()
    @Override
    public Warehouse getWarehouseById(UUID id) {
        return warehouseRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("SpringCacheableMethodCallsInspection")
    @Override
    @CachePut()
    public Warehouse updateWarehouse(Warehouse warehouse) {
        return Objects.nonNull(warehouse.getId()) && Objects.nonNull(getWarehouseById(warehouse.getId())) ?
                warehouseRepository.save(warehouse) : null;
    }


    @Override
    @CacheEvict()
    public void deleteWarehouse(UUID id) {
        warehouseRepository.deleteById(id);
    }


    @Override
    public Long getExistAmount(UUID id) {
        return storageRepo.findByWarehouse_Id(id)
                .stream()
                .mapToLong(GoodStorage::getAmount)
                .sum();
    }

    @Override
    public boolean isFull(UUID id) {
        return warehouseRepository.getReferenceById(id).getMaxAmount() <= getExistAmount(id);
    }

    @Override
    @Cacheable()
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

}
