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
    Warehouse addWarehouse(Warehouse warehouse);

    Warehouse getWarehouseById(UUID id);

    Warehouse updateWarehouse(Warehouse warehouse);

    void deleteWarehouse(UUID id);

    Long getExistAmount(UUID id);

    boolean isFull(UUID id);

    List<Warehouse> getAllWarehouses();
}
