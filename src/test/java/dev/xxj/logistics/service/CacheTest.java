package dev.xxj.logistics.service;

import dev.xxj.logistics.model.Good;
import dev.xxj.logistics.model.Warehouse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CacheTest {
    @Autowired
    private GoodService goodService;
    @Autowired
    private WarehouseService warehouseService;
    private static Good good;
    private static Warehouse house;

    @Test
    void t01contextLoads() {
    }

    @Test
    void t02getAllGoods1() {
        goodService.getAllGoods();
    }

    @Test
    void t02getAllGoods2() {
        goodService.getAllGoods();
    }

    @Test
    void t02getAllGoods3() {
        goodService.getAllGoods();
    }

    @Test
    void t03getOneGood1() {
        good = goodService.getGoodByName("banana").getFirst();
        assertEquals("banana", good.getName());
        assertNotNull(good.getId());
    }

    @Test
    void t03getOneGood2() {
        var goodById = goodService.getGoodById(good.getId());
        assertEquals(good, goodById);
    }

    @Test
    void t03getOneGood3() {
        var goodById = goodService.getGoodById(good.getId());
        assertEquals(good, goodById);
    }


    @Test
    void t04getAllHouse1() {
        warehouseService.getAllWarehouses();
    }

    @Test
    void t04getAllHouse2() {
        warehouseService.getAllWarehouses();
    }

    @Test
    void t04getAllHouse3() {
        warehouseService.getAllWarehouses();
    }

    @Test
    void t05getOneHouse1() {
        house = warehouseService.getAllWarehouses().getFirst();
        assertNotNull(house.getId());
    }

    @Test
    void t05getOneHouse2() {
        var warehouseById = warehouseService.getWarehouseById(house.getId());
        assertEquals(house, warehouseById);
    }

    @Test
    void t05getOneHouse3() {
        var warehouseById = warehouseService.getWarehouseById(house.getId());
        assertEquals(house, warehouseById);
    }


}
