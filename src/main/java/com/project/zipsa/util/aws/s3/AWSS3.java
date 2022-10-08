package com.project.zipsa.util.aws.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.project.zipsa.util.aws.AWSCredentials;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;

@Slf4j
public class AWSS3 {
    private AmazonS3 amazonS3;
    private AWSCredentials awsCredentials;

    public AWSS3(AWSCredentials awsCredentials) {
        this.awsCredentials = awsCredentials;
    }

    private void initialize() {
        this.amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(this.awsCredentials.getAWSCredentialsProvider())
                .withRegion(awsCredentials.getRegion()).build();
    }

    public PutObjectResult upload(String bucketName, InputStream targetInputStream, String key, String fileName, Long size) throws IOException {
        try {
            AmazonS3 s3 = this.getAmazonS3();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(size);
            metadata.setContentType(URLConnection.guessContentTypeFromName(fileName));
            return s3.putObject(new PutObjectRequest(bucketName, key, targetInputStream, metadata));
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public S3Object s3GetObject(String bucketName, String key) {
        return this.getAmazonS3().getObject(new GetObjectRequest(bucketName, key));
    }

    public AmazonS3 getAmazonS3() {
        if (this.amazonS3 == null) {
            this.initialize();
        }

        return this.amazonS3;
    }

    public boolean isExist(String bucketName, String key) {
        return this.getAmazonS3().doesObjectExist(bucketName, key);
    }

    public void move(String bucketName, String srcKey, String destKey) {
        AmazonS3 amazonS3 = this.getAmazonS3();
        amazonS3.copyObject(new CopyObjectRequest(bucketName, srcKey, bucketName, destKey));
        amazonS3.deleteObject(new DeleteObjectRequest(bucketName, srcKey));
    }

    public void delete(String bucket, String key) {
        this.getAmazonS3().deleteObject(new DeleteObjectRequest(bucket, key));
    }

    public String getFileURL(String s3FileFullPath) {

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + 1000 * 60 * 5;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(awsCredentials.getBucket(), s3FileFullPath)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(expiration);
        String preSignedURL = getAmazonS3().generatePresignedUrl(generatePresignedUrlRequest).toString();
        log.info("pre-signed url = {}", preSignedURL);
        return preSignedURL;
    }
}
