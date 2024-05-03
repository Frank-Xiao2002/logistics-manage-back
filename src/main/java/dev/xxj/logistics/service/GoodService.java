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

    /**
     * Get a good by its id.
     *
     * @param id UUID of the good
     * @return the good object with the specified id
     */
    Good getGoodById(UUID id);

    /**
     * Get a good by its name.
     *
     * @param name name of the good
     * @return a list of goods with the specified name
     */
    List<Good> getGoodByName(String name);

    /**
     * Update a good's details.
     *
     * @param good the good object to be updated, should contain the id of the good
     * @return the updated good object if the good is registered, null otherwise
     * @see Good
     */
    Good updateGood(Good good);

    /**
     * Delete a good by its id.
     *
     * @param id UUID of the good
     */
    void deleteGood(UUID id);

    /**
     * Get all goods in the system.
     *
     * @return a list of all goods in the system
     */
    List<Good> getAllGoods();

    /**
     * Get all locations of one good.
     *
     * @param id UUID of the good
     * @return a list of {@link Warehouse} objects where the good is stored
     */
    List<Warehouse> getLocations(UUID id);

    /**
     * Get the total stored amount of a good in the system.
     *
     * @param id UUID of the good
     * @return the total amount of the good in the system
     */
    Long getTotalAmount(UUID id);

    /**
     * Move a good from one warehouse to another.
     *
     * @param moveDTO dto object containing the good id, source warehouse id, target warehouse id, and amount to move
     * @return true if the move is successful, false otherwise
     */
    boolean moveGood(MoveDTO moveDTO);

    /**
     * Get all {@link GoodStorage} objects of a good.
     *
     * @param goodId UUID of the good
     * @return a list of {@link GoodStorage} objects of the good
     */
    List<GoodStorage> getGoodStorages(UUID goodId);

    /**
     * Store an amount of good into a warehouse
     *
     * @param goodStorageDto dto object containing the good id, warehouse id, and amount to store
     * @return the {@link GoodStorage} object if the storage is successful, null otherwise
     */
    GoodStorage storeGood(GoodStorageDto goodStorageDto);

    /**
     * Retrieve an amount of good from a warehouse
     *
     * @param goodStorageDto dto object containing the good id, warehouse id, and amount to retrieve
     */
    void retrieveGood(GoodStorageDto goodStorageDto);

}
