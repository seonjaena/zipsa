package com.project.zipsa.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.project.zipsa.entity.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public void save(Test test) {
        dynamoDBMapper.save(test);
    }

}
