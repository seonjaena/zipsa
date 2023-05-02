package com.project.zipsa.config;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class EmbeddedDynamoDBConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DynamoDBProxyServer server;

    @PostConstruct
    public void start() {
        if(server != null) {
            return;
        }

        try {
            AwsDynamoDbLocalTestUtils.initSqLite();
            server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory"});
            server.start();
            logger.info("Local DynamoDB를 시작 합니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void stop() {
        if(server == null) {
            return;
        }

        try {
            server.stop();
            logger.info("Local DynamoDB를 종료 합니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
