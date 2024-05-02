package dev.xxj.logistics.service;

import dev.xxj.logistics.model.Warehouse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
class WarehouseServiceTest {
    @Autowired
    private WarehouseService service;

    @Test
    void t1contextLoads() {
    }

    @Test
    void t2updateWarehouse() {
        Warehouse added = service.addWarehouse(Warehouse.builder()
                .name("w-n")
                .location("Beijing")
                .maxAmount(300L).build());
        System.out.println("added = " + added);
        added.setMaxAmount(500L);
        Warehouse updated = service.updateWarehouse(added);
        System.out.println("updated = " + updated);
    }

}