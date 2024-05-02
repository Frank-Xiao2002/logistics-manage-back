package dev.xxj.logistics.repo;

import dev.xxj.logistics.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    List<Warehouse> deleteByName(String name);

    Optional<Warehouse> findByName(String name);
}