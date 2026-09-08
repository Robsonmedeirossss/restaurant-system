package com.dev.restaurant.config.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration 
@RequiredArgsConstructor 
public class SqsConfig {

    private final AwsCredentialsProvider awsCredentials;
    
    @Bean(name = "SqsClientBean")
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
            .credentialsProvider(awsCredentials)
            .region(Region.US_EAST_1)
        .build();
    }
}
