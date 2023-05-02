package com.project.zipsa.custom.annotation;

import com.project.zipsa.common.TestExecutionListener;
import com.project.zipsa.common.TestReady;
import com.project.zipsa.config.DynamoDBConfig;
import com.project.zipsa.config.EmbeddedDynamoDBConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles({"test"})
@AutoConfigureMockMvc
@Import({TestReady.class, DynamoDBConfig.class, EmbeddedDynamoDBConfig.class})
@TestExecutionListeners(value = {TestExecutionListener.class}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
public @interface AllConfigTestAnnotation {
}
