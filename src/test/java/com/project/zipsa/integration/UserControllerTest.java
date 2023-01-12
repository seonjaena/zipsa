package com.project.zipsa.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.zipsa.custom.annotation.AllConfigTestAnnotation;
import com.project.zipsa.dto.auth.LoginRequestDto;
import com.project.zipsa.common.TestReady;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AllConfigTestAnnotation
@DisplayName("사용자관련 서비스 레이어 단위 테스트")
public class UserControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestReady testReady;

    @BeforeEach
    void setUp() {
        testReady.ready();
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccess() throws Exception {
        String content = objectMapper.writeValueAsString(
                new LoginRequestDto("test-normal-user@yahoo.com", "Test-user-pwd-12-!@")
        );
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/user/login")
                                .header("Authorization", "Bearer ")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
