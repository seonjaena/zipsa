package com.project.zipsa.util;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import lombok.Getter;

@Getter
public class AWSCredentials {

    private String accessKey;
    private String secretKey;
    private String region;

    public AWSCredentials(String accessKey, String secretKey, String region) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region.trim().equals("")? "ap-northeast-2" : region;
        System.out.println("access key = " + accessKey);
        System.out.println("secret key = " + secretKey);
        System.out.println("region = " + region);
    }

    public AWSCredentialsProvider getAWSCredentialsProvider() {
        if(accessKey == null || accessKey.trim().isEmpty() || secretKey == null || secretKey.trim().isEmpty()) {
            return InstanceProfileCredentialsProvider.getInstance();
        }
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }

}
