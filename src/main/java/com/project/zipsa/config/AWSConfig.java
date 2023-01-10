package com.project.zipsa.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.project.zipsa.util.aws.AWSCredentials;
import com.project.zipsa.util.aws.s3.AWSS3;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AWSConfig {

    @Value("${cloud.aws.credentials.access-key:}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key:}")
    private String secretKey;

    @Value("${cloud.aws.region.static:ap-northeast-2}")
    private String region;

    @Value("${cloud.aws.dynamodb.endpoint}")
    private String dynamodbEndpoint;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Bean
    public AWSCredentials awsCredentials() {
        return new AWSCredentials(accessKey, secretKey, bucket, region);
    }

    @Bean
    public AWSS3 awss3() {
        return new AWSS3(awsCredentials());
    }

    @Bean
    @Profile({"dev", "prod"})
    public DynamoDBMapper dynamoDBMapper() {
        return new DynamoDBMapper(buildAmazonDynamoDB());
    }

    private AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(dynamodbEndpoint,region))
                .withCredentials(awsCredentials().getAWSCredentialsProvider())
                .build();
    }

}
