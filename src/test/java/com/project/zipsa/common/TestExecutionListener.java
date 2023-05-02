package com.project.zipsa.common;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.ArrayList;
import java.util.List;

public class TestExecutionListener extends AbstractTestExecutionListener {

    @Override
    public void beforeTestClass(TestContext testContext) {
        AmazonDynamoDB dynamoDB = testContext.getApplicationContext().getBean(AmazonDynamoDB.class);
        createDynamoTable(dynamoDB);
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        AmazonDynamoDB dynamoDB = testContext.getApplicationContext().getBean(AmazonDynamoDB.class);
        dropDynamoTable(dynamoDB);
    }

    private void createDynamoTable(AmazonDynamoDB dynamoDB) {
        String tableName = "REFRESH_TOKEN";
        String hashKey = "USER_ID";
        String rangeKey = "REFRESH_TOKEN";

        List<AttributeDefinition> attributeDefinitions = new ArrayList<>();
        attributeDefinitions.add(new AttributeDefinition(hashKey, ScalarAttributeType.S));
        attributeDefinitions.add(new AttributeDefinition(rangeKey, ScalarAttributeType.S));

        List<KeySchemaElement> ks = new ArrayList<>();
        ks.add(new KeySchemaElement(hashKey, KeyType.HASH));
        ks.add(new KeySchemaElement(rangeKey, KeyType.RANGE));

        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput(1000L, 1000L);

        CreateTableRequest request = new CreateTableRequest()
                .withTableName(tableName)
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(ks)
                .withProvisionedThroughput(provisionedThroughput);

        dynamoDB.createTable(request);
    }

    private void dropDynamoTable(AmazonDynamoDB dynamoDB) {
        String tableName = "REFRESH_TOKEN";
        dynamoDB.deleteTable(tableName);
    }
}
