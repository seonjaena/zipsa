package com.project.zipsa.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.project.zipsa.entity.RefreshToken;
import com.project.zipsa.exception.custom.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public void save(RefreshToken refreshToken) {
        dynamoDBMapper.save(refreshToken);
    }

    public RefreshToken findByValue(String refreshToken) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":value", new AttributeValue().withS(refreshToken));
        DynamoDBScanExpression scan = new DynamoDBScanExpression()
                .withFilterExpression("VALUE = :value")
                .withExpressionAttributeValues(eav);
        List<RefreshToken> results = dynamoDBMapper.scan(RefreshToken.class, scan);
        if(results.size() != 1) {
            throw new RefreshTokenNotFoundException("error.jwt.refresh.notfound");
        }
        return results.get(0);
    }

}
