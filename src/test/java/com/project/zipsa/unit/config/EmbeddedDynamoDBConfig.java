package com.project.zipsa.unit.config;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import org.springframework.boot.test.context.TestConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@TestConfiguration
public class EmbeddedDynamoDBConfig {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
