package com.project.zipsa.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamoDBTable(tableName = "REFRESH_TOKEN")
public class RefreshToken {

    @DynamoDBHashKey(attributeName = "KEY")
    private String key;

    @DynamoDBAttribute(attributeName = "VALUE")
    private String value;

}
