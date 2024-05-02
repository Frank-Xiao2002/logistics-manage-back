package dev.xxj.logistics.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@SpringBootTest
@AutoConfigureMockMvc
class UserHeaderFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails
    void t1testHeader() throws Exception {
        mockMvc.perform(get("/good"))
                .andDo(print())
                .andExpect(header().string("Current-User", "user"));
    }


    @Test
    @WithUserDetails("admin")
    void t2testHeader() throws Exception {
        mockMvc.perform(get("/good"))
                .andDo(print())
                .andExpect(header().string("Current-User", "admin"));
    }


    @Test
    void t3testHeader() throws Exception {
        mockMvc.perform(get("/good"))
                .andDo(print())
                .andExpect(header().doesNotExist("Current-User"));
    }


}