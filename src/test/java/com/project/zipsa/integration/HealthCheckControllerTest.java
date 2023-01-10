package com.project.zipsa.integration;

import com.project.zipsa.custom.annotation.AllConfigTestAnnotation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AllConfigTestAnnotation
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void taskHealthCheck401() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api/healthcheck/task"))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        });
    }

    @Test
    void taskHealthCheck200() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/healthcheck/task")
                                .header("Authorization", "Bearer ")
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void albHealthCheck200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/healthcheck/alb"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
