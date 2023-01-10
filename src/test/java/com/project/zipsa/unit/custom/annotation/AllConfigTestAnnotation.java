package com.project.zipsa.unit.custom.annotation;

import com.project.zipsa.unit.common.TestReady;
import com.project.zipsa.unit.config.DynamoDBConfig;
import com.project.zipsa.unit.config.EmbeddedDynamoDBConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles({"test"})
@SpringBootTest
@AutoConfigureMockMvc
@Import({TestReady.class, DynamoDBConfig.class, EmbeddedDynamoDBConfig.class})
public @interface AllConfigTestAnnotation {
}
