package dev.xxj.logistics.controller;

import dev.xxj.logistics.model.Warehouse;
import dev.xxj.logistics.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    private final WarehouseService service;

    @Autowired
    public WarehouseController(WarehouseService warehouseService) {
        this.service = warehouseService;
    }


    @PostMapping
    public ResponseEntity<Warehouse> addWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse added = service.addWarehouse(warehouse);
        return Objects.nonNull(added) ?
                ResponseEntity.status(HttpStatus.CREATED).body(added) : ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        return ResponseEntity.ok(service.getAllWarehouses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouseById(@PathVariable UUID id) {
        Warehouse warehouse = service.getWarehouseById(id);
        return Objects.isNull(warehouse) ? ResponseEntity.notFound().build() : ResponseEntity.ok(warehouse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Warehouse> deleteWarehouse(@PathVariable UUID id) {
        service.deleteWarehouse(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Warehouse> updateWarehouse(@RequestBody Warehouse warehouse) {
        var updated = service.updateWarehouse(warehouse);
        return Objects.nonNull(updated) ? ResponseEntity.ok(updated) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/full/{id}")
    public ResponseEntity<String> isFull(@PathVariable UUID id) {
        return ResponseEntity.ok(service.isFull(id) ? "Full" : "Not Full");
    }

    @GetMapping("/amount/{id}")
    public ResponseEntity<String> getExistAmount(@PathVariable UUID id) {
        return ResponseEntity.ok(String.format("Exist amount: %d", service.getExistAmount(id)));
    }

}
