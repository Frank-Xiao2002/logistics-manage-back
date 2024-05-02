package dev.xxj.logistics.repo;

import dev.xxj.logistics.model.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest()
class WarehouseRepositoryTest {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Test
    void t1testSave() {
        Warehouse w1 = Warehouse.builder()
                .name("warehouse-876")
                .location("China")
                .maxAmount(1000L).build();
        Warehouse save1 = warehouseRepository.save(w1);
        assertAll(
                () -> assertEquals("warehouse-876", save1.getName()),
                () -> assertEquals("China", save1.getLocation()),
                () -> assertEquals(1000L, save1.getMaxAmount()),
                () -> assertTrue(Objects.nonNull(save1.getId()))
        );
    }

}