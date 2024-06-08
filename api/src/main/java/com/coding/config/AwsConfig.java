package com.coding.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AwsConfig {
    private final AppProperties appProperties;

    public AwsConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public S3AsyncClient s3AsyncClient() {
        AppProperties.S3 s3 = appProperties.s3();
        AwsBasicCredentials credentials = AwsBasicCredentials.create(s3.accessKey(), s3.secretKey());
        return S3AsyncClient.builder()
                .region(Region.of(s3.region()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        AppProperties.S3 s3 = appProperties.s3();
        AwsBasicCredentials credentials = AwsBasicCredentials.create(s3.accessKey(), s3.secretKey());
        try(S3Presigner presigner = S3Presigner.builder()
                .region(Region.of(s3.region()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build()) {
            return presigner;
        }
    }
}
