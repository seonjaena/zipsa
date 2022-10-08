package com.project.zipsa.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "REFRESH_TOKEN")
public class RefreshToken {

    @DynamoDBHashKey(attributeName = "USER_ID")
    private String userId;

    @DynamoDBRangeKey(attributeName = "REFRESH_TOKEN")
    private String refreshToken;

}
