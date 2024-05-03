package dev.xxj.logistics.service;

import dev.xxj.logistics.model.Warehouse;

import java.util.List;
import java.util.UUID;

/**
 * WarehouseService interface serves as the service for warehouse operations.
 * It provides the basic CRUD operations for warehouses and the operations for checking the storage status.
 * Moreover, it enables the user to check the storage status and the existence of goods in the warehouse.
 *
 * @author Frank-Xiao
 * @see Warehouse
 */
public interface WarehouseService {
    /**
     * Add a new warehouse to the system.
     *
     * @param warehouse the warehouse to be added
     * @return the warehouse added
     */
    Warehouse addWarehouse(Warehouse warehouse);

    /**
     * Get the warehouse by its id.
     *
     * @param id the id of the warehouse, of type UUID
     * @return the warehouse with the given id
     */
    Warehouse getWarehouseById(UUID id);

    /**
     * Update the warehouse.
     *
     * @param warehouse the warehouse to be updated, should contain the id of the warehouse
     * @return the updated warehouse
     */
    Warehouse updateWarehouse(Warehouse warehouse);

    /**
     * Delete the warehouse by its id.
     *
     * @param id the id of the warehouse, of type UUID
     */
    void deleteWarehouse(UUID id);

    /**
     * Get the stored amount of goods in one warehouse.
     *
     * @param id the id of the warehouse, of type UUID
     * @return the amount of goods in the warehouse
     */
    Long getExistAmount(UUID id);

    /**
     * Check if one warehouse is full.
     *
     * @param id the id of the warehouse to be checked, of type UUID
     * @return true if the warehouse is full, false otherwise
     */
    boolean isFull(UUID id);

    /**
     * Get all the warehouses in the system.
     *
     * @return a list of all the warehouses
     */
    List<Warehouse> getAllWarehouses();
}
