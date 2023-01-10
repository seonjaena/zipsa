package com.project.zipsa.unit.common;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.project.zipsa.dto.auth.TokenDto;
import com.project.zipsa.entity.Users;
import com.project.zipsa.entity.enums.USER_ROLE;
import com.project.zipsa.repository.UserRepository;
import com.project.zipsa.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@TestComponent
public class TestReady {

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AmazonDynamoDB dynamoDB;

    private static final String NORMAL_USER_ID = "test-normal-user@yahoo.com";
    private static final String NORMAL_USER_NM = "일반인";
    private static final String NORMAL_USER_PN = "010-1111-1111";
    private static final String ADMIN_USER_ID = "test-admin-user@yahoo.com";
    private static final String ADMIN_USER_NM = "관리자";
    private static final String ADMIN_USER_PN = "010-2222-2222";
    private static final String USER_PW = "Test-user-pwd-12-!@";
    private static final LocalDate USER_BIRTH = LocalDate.of(1998, 5, 22);

    public void ready() {
        makeDynamoDBTable();
        addTestUser();

        // 테스트용 유저(일반)의 token
        TokenDto normalToken = jwtProvider.createToken(NORMAL_USER_ID, List.of(USER_ROLE.USER.getText()));
        // 테스트용 유저(어드민)의 token
        TokenDto adminToken = jwtProvider.createToken(ADMIN_USER_ID, List.of(USER_ROLE.ADMIN.getText()));
    }

    private void addTestUser() {
        Users normalUser = Users.builder(NORMAL_USER_ID, passwordEncoder.encode(USER_PW), NORMAL_USER_NM,
                                        NORMAL_USER_NM, USER_BIRTH, false, NORMAL_USER_PN).build();
        userRepository.saveAndFlush(normalUser);
        Users adminUser = Users.builder(ADMIN_USER_ID, passwordEncoder.encode(USER_PW), ADMIN_USER_NM,
                                        ADMIN_USER_NM, USER_BIRTH, false, ADMIN_USER_PN).build();
        userRepository.saveAndFlush(adminUser);
    }

    private void makeDynamoDBTable() {
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

        CreateTableResult res = dynamoDB.createTable(request);
    }

}
