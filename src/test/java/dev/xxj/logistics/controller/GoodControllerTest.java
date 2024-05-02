package dev.xxj.logistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.xxj.logistics.model.Good;
import dev.xxj.logistics.model.GoodStorageDto;
import dev.xxj.logistics.model.MoveDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
@Slf4j
class GoodControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    private static Good good;
    private final String newName = "mouse Logitech K2750000";
    private static final Random random = new Random();
    /**
     * This warehouseId is used for testing the storeGood method.
     */
    private static final UUID houseId1 = UUID.fromString("feb0836c-376a-4700-8f12-5af5bb80562c");
    private static final UUID houseId2 = UUID.fromString("d8be154a-da4f-4699-bf14-4456119de4a8");
    private static final Long total = random.nextLong(50, 100);
    private static final Long retrieved = random.nextLong(1, 50);


    @Test
    void t01contextLoads() {
    }

    @Test
    @WithUserDetails
    void t02getAllGoods() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/good"))
                .andExpect(status().isOk()).andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @WithUserDetails("admin")
    void t03addGood() throws Exception {
        good = Good.builder().name("mouse Logitech K275").build();
        MvcResult mvcResult = mvc.perform(post("/good")
                        .content(objectMapper.writeValueAsString(good))
                        .contentType("application/json"))
//                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        good = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Good.class);
    }

    @Test
    @WithUserDetails("admin")
    void t04updateGood() throws Exception {
        good.setName(newName);
        MvcResult mvcResult = mvc.perform(put("/good")
                        .content(objectMapper.writeValueAsString(good))
                        .contentType("application/json"))
//                .andDo(print())
                .andExpect(status().isAccepted()).andReturn();
        var updated = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Good.class);
        assertEquals(newName, updated.getName());
    }


    @Test
    @WithUserDetails
    void t05getGoodById() throws Exception {
        var str = mvc.perform(get("/good/" + good.getId()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        var good1 = objectMapper.readValue(str, Good.class);
        assertEquals(good, good1);
    }

    @Test
    void t06getGoodByName1() throws Exception {
        assertEquals(newName, good.getName());
        mvc.perform(get("/good/name/" + newName))
                .andExpect(status().is(401));
    }

    @Test
    @WithUserDetails
    void t06getGoodByName2() throws Exception {
        assertEquals(newName, good.getName());
        mvc.perform(get("/good/name/" + newName))
                .andExpect(status().isOk());
    }


    /**
     * This test case is used to test the storeGood method.
     * The warehouse with houseId1 is used to store the good, which has a maximum capacity of 200.
     * Clearly, the amount of the good is greater than the maximum capacity of the warehouse.
     */
    @Test
    @WithUserDetails("admin")
    void t07storeGood1Bad() throws Exception {
        GoodStorageDto dto = new GoodStorageDto(houseId1, good.getId(), random.nextLong(200, 250));
        mvc.perform(post("/good/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithUserDetails("admin")
    void t07storeGood2Ok() throws Exception {
        GoodStorageDto dto = new GoodStorageDto(houseId1, good.getId(), total);
        mvc.perform(post("/good/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("admin")
    void t07storeGood3Ok() throws Exception {
        GoodStorageDto dto = new GoodStorageDto(houseId1, good.getId(), 3L);
        mvc.perform(post("/good/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("admin")
    void t07storeGood4Bad() throws Exception {
        GoodStorageDto dto = new GoodStorageDto(houseId1, good.getId(), 1000L);
        mvc.perform(post("/good/store")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithUserDetails("admin")
    void t08retrieveGood1NotSuccess() throws Exception {
        GoodStorageDto dto = new GoodStorageDto(houseId1, good.getId(), random.nextLong(100, 150));
        /*
         * The amount of the good to be retrieved is greater than the amount of the good stored in the warehouse,
         * which will cause the retrieval to fail.
         */
        mvc.perform(post("/good/retrieve")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithUserDetails("admin")
    void t08retrieveGood2Success() throws Exception {
        GoodStorageDto dto = new GoodStorageDto(houseId1, good.getId(), retrieved);
        /*
         * The amount of the good to be retrieved is smaller than the amount of the good stored in the warehouse,
         * which should succeed.
         */
        mvc.perform(post("/good/retrieve")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("admin")
    void t10moveGood1() throws Exception {
        MoveDTO moveDTO = new MoveDTO(houseId1, houseId2, good.getId(), 5L);
        mvc.perform(post("/good/move")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(moveDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails("admin")
    void t10moveGood2() throws Exception {
        MoveDTO moveDTO = new MoveDTO(houseId1, houseId2, good.getId(), 300L);
        mvc.perform(post("/good/move")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(moveDTO)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithUserDetails
    void t11getGoodStorages() throws Exception {
        mvc.perform(get("/good/storages/" + good.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails
    void t12getLocations() throws Exception {
        mvc.perform(get("/good/locations/" + good.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails
    void t13getTotalAmount() throws Exception {
        var computedAmount = mvc.perform(get("/good/amount/" + good.getId()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals(total + 3L - retrieved, Long.parseLong(computedAmount));
    }

    @Test
    @WithUserDetails("admin")
    void t17deleteGood() throws Exception {
        mvc.perform(delete("/good/" + good.getId()))
                .andExpect(status().isOk());
    }

}