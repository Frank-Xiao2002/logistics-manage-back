package dev.xxj.logistics.service;

import dev.xxj.logistics.model.*;

import java.util.List;
import java.util.UUID;

/**
 * GoodService interface serves as the most important service in the system.
 * It provides the basic CRUD operations for goods and the operations for moving goods between warehouses.
 * Moreover, it enables the user to check good locations and storage status.
 *
 * @author Frank-Xiao
 * @see Good
 * @see GoodStorage
 * @see Warehouse
 */
public interface GoodService {
    /**
     * Register a good into the system.
     *
     * @param good the good object to be registered, which should not contain an id
     * @return the registered good object if the good is not registered, null otherwise
     */
    Good addGood(Good good);

    Good getGoodById(UUID id);

    List<Good> getGoodByName(String name);

    /**
     * Update a good's details.
     *
     * @param good the good object to be updated, should contain the id of the good
     * @return the updated good object if the good is registered, null otherwise
     * @see Good
     */
    Good updateGood(Good good);

    void deleteGood(UUID id);

    List<Good> getAllGoods();

    List<Warehouse> getLocations(UUID id);

    Long getTotalAmount(UUID id);

    boolean moveGood(MoveDTO moveDTO);

    List<GoodStorage> getGoodStorages(UUID goodId);

    GoodStorage storeGood(GoodStorageDto goodStorageDto);

    void retrieveGood(GoodStorageDto goodStorageDto);

}
