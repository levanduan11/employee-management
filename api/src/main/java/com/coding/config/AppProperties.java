package com.coding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "app")
public record AppProperties(
        String ui_url,
        RSAProperties rsa,
        JwtTokenProperties jwt,
        S3 s3
) {
    public record RSAProperties(
            RSAPrivateKey privateKey,
            RSAPublicKey publicKey) {
    }

    public record JwtTokenProperties(
            long expiration,
            long rememberMeTokenExpiration,
            String issuer) {
    }

    public record S3(
            String accessKey,
            String secretKey,
            String bucket,
            String region
    ) {

    }
}
