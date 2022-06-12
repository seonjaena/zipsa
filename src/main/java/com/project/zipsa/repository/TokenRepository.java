package com.project.zipsa.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.project.zipsa.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor
public class TokenRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public void save(RefreshToken refreshToken) {
        dynamoDBMapper.save(refreshToken);
    }

}
