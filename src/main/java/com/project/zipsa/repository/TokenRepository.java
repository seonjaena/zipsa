package com.project.zipsa.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.project.zipsa.entity.RefreshToken;
import com.project.zipsa.exception.custom.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final DynamoDBMapper dynamoDBMapper;
    private final MessageSource messageSource;

    public void save(RefreshToken refreshToken) {
        dynamoDBMapper.save(refreshToken);
    }

    public RefreshToken findOneByToken(String refreshToken) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(refreshToken));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("REFRESH_TOKEN = :val1")
                .withExpressionAttributeValues(eav);


        List<RefreshToken> results =  dynamoDBMapper.scan(RefreshToken.class, scanExpression);

        if(results.size() != 1) {
            throw new RefreshTokenNotFoundException(messageSource.getMessage("error.jwt.refresh.notfound", null, Locale.KOREA));
        }
        return results.get(0);
    }

    public List<RefreshToken> findAllById(String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<RefreshToken> queryExpression = new DynamoDBQueryExpression<RefreshToken>()
                .withKeyConditionExpression("USER_ID = :val1")
                .withExpressionAttributeValues(eav);
        return dynamoDBMapper.query(RefreshToken.class, queryExpression);
    }

    public void delete(String userId) {
        for(RefreshToken token: findAllById(userId)) {
            dynamoDBMapper.delete(token);
        }
    }

}
