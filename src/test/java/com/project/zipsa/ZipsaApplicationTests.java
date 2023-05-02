package com.project.zipsa;

import com.project.zipsa.controller.HealthCheckController;
import com.project.zipsa.entity.QUsers;
import com.project.zipsa.entity.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles(profiles = {"test"})
@WebMvcTest(controllers = { HealthCheckController.class})
class ZipsaApplicationTests {

    @Autowired
    private HealthCheckController healthCheckController;

    @Autowired
    private JPAQueryFactory query;

    @Test
    void contextLoads() {
        assertThat(healthCheckController).isNotNull();
    }

    @Test
    void test() {
        List<Users> results = query
                .selectFrom(QUsers.users)
                .fetch();

        Assertions.assertThat(results.size()).isSameAs(10);
    }

}
