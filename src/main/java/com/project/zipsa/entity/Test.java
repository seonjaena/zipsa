package com.project.zipsa.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamoDBTable(tableName = "LOGS")
public class Test {

    @DynamoDBHashKey(attributeName = "LOG_ID")
    private String logId;

    @DynamoDBRangeKey(attributeName = "CREATED_DATETIME")
    private String createdDatetime;

}
