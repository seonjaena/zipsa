package com.project.zipsa.integration;

import com.project.zipsa.common.TestReady;
import com.project.zipsa.custom.annotation.AllConfigTestAnnotation;
import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.security.JwtProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@AllConfigTestAnnotation
public class HealthCheckControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestReady testReady;

    @BeforeEach
    void setUp() {
        testReady.ready();
    }

    @Test
    void taskHealthCheck401() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.mockMvc.perform(MockMvcRequestBuilders.get("/api/healthcheck/task"))
                    .andExpect(MockMvcResultMatchers.status().isUnauthorized());
        });
    }

    @Test
    void taskHealthCheck200(@Autowired JwtProvider jwtProvider) throws Exception {
        TokenDto token = jwtProvider.createToken("test-normal-user@yahoo.com", List.of(USER_ROLE.USER.getText()));
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/healthcheck/task")
                                .header("Authorization", "Bearer " + token.getAccessToken())
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void albHealthCheck200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/healthcheck/alb"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
