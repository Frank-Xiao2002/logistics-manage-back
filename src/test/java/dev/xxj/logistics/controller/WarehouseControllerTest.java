package dev.xxj.logistics.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.xxj.logistics.model.Warehouse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.MethodName.class)
class WarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    private final String[] locations = {"Brazil", "Russia", "China", "USA", "France"};
    private static Warehouse warehouse;

    @Test
    void t01contextLoads() {
    }

    @Test
    @WithUserDetails
    void t02addWarehouse1() throws Exception {
        mockMvc.perform(post("/warehouse")
                        .with(user("user").password("password")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("admin")
    void t03addWarehouse2() throws Exception {
        Random random = new Random();
        Warehouse warehouse = Warehouse.builder()
                .name("warehouse-" + random.nextInt(100, 1000))
                .location(locations[random.nextInt(0, locations.length)])
                .maxAmount(random.nextLong(100, 501)).build();
        mockMvc.perform(post("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouse)))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails("admin")
    void t04addWarehouse3() throws Exception {
        Random random = new Random();
        Warehouse house = Warehouse.builder()
                .name("warehouse-" + random.nextInt(100, 1000))
                .location(locations[random.nextInt(0, locations.length)])
                .maxAmount(random.nextLong(100, 501)).build();
        MvcResult mvcResult = mockMvc.perform(post("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(house)))
                .andDo(print())
                .andExpect(status().isCreated()).andReturn();
        warehouse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Warehouse.class);
    }


    @Test
    @WithUserDetails
    void t05getAllWarehouses1() throws Exception {
        mockMvc.perform(get("/warehouse"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails
    void t06getWarehouseById1() throws Exception {
        UUID id = UUID.fromString("15f97304-1ecc-4eb9-88e1-c2a66dc40719");
        mockMvc.perform(get("/warehouse/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    @WithUserDetails
    void t07getWarehouseById2() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(get("/warehouse/" + id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    @WithUserDetails("admin")
    void t08addWarehouse() throws Exception {
        mockMvc.perform(post("/warehouse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(warehouse)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("admin")
    void t08updateWarehouse() throws Exception {
        Random random = new Random();
        warehouse.setMaxAmount(random.nextLong(100, 501));
        var content = put("/warehouse")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(warehouse));
        MvcResult mvcResult = mockMvc.perform(content)
//                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        var updated = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                Warehouse.class);
        assertEquals(warehouse.getMaxAmount(), updated.getMaxAmount());
        assertEquals(warehouse.getId(), updated.getId());
    }


    @Test
    void t09deleteWarehouse1() throws Exception {
        mockMvc.perform(delete("/warehouse/"))
                .andDo(print())
                .andExpect(status().is(401));
    }


    @Test
    @WithUserDetails
    void t10deleteWarehouse2() throws Exception {
        mockMvc.perform(delete("/warehouse/"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails
    void t11getAllWarehouses2() throws Exception {
        mockMvc.perform(get("/warehouse"))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithUserDetails
    void t12isFull() throws Exception {
        var content = mockMvc.perform(get("/warehouse/full/" + warehouse.getId()))
                .andDo(print())
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        assertEquals("Not Full", content);
    }

    @Test
    @WithUserDetails
    void t13getExistAmount() throws Exception {
        mockMvc.perform(get("/warehouse/amount/" + warehouse.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Exist amount: 0"));
    }


    @Test
    @WithUserDetails("admin")
    void t20deleteWarehouse3() throws Exception {
        mockMvc.perform(delete("/warehouse/" + warehouse.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

}