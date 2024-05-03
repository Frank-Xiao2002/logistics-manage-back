package dev.xxj.logistics.repo;

import dev.xxj.logistics.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * WarehouseRepository interface to allow CRUD and paging operations on Warehouse objects.
 * <p>
 * WarehouseRepository extends {@link JpaRepository}, a Spring Data interface. It provides methods for CRUD operations
 * and paging on Warehouse objects.
 *
 * @author Frank-Xiao
 * @see JpaRepository
 * @see Warehouse
 */
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
}