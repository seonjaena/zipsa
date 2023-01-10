package com.project.zipsa;

import com.project.zipsa.controller.HealthCheckController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@WebMvcTest(controllers = { HealthCheckController.class})
class ZipsaApplicationTests {

    @Autowired
    private HealthCheckController healthCheckController;

    @Test
    void contextLoads() {
        assertThat(healthCheckController).isNotNull();
    }

}
