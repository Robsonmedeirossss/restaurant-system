package com.dev.restaurant.aws;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
@RequiredArgsConstructor  
public class S3Storage {
    
    private final S3AsyncClient s3Client;

    @Value("${spring.cloud.s3.bucket-name}")
    private String bucketName;

    public CompletableFuture<String> uploadFile(byte[] filedata, String filename, String contentType) {
        String key = UUID.randomUUID().toString() + "-" + filename;

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(this.bucketName)
            .key(key)
            .contentType(contentType)
            .build();


            return s3Client.putObject(putObjectRequest, AsyncRequestBody.fromBytes(filedata)).thenApply(response -> 
                String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, Region.US_EAST_1.id(), key));

    }

}
