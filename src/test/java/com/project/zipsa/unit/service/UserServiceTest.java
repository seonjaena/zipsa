package com.project.zipsa.unit.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@Nested
@DisplayName("User Test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Nested
    @DisplayName("Login Test")
    class UserLoginTest {
        @Test
        @DisplayName("Login Success")
        void loginSuccess() {
            Assertions.assertThat("a").isEqualTo("a");
        }
    }

}
