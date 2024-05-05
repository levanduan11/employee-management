package com.coding.service.impl;

import com.coding.config.AppProperties;
import com.coding.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.stream.Collectors;

import static com.coding.core.constant.AppConstant.AUTHORITIES_CLAIM;

@Service
class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final JwtEncoder jwtEncoder;
    private final AppProperties appProperties;

    public AuthServiceImpl(
            JwtEncoder jwtEncoder,
            AppProperties appProperties) {
        this.jwtEncoder = jwtEncoder;
        this.appProperties = appProperties;
    }

    public String generateAccessToken(Authentication authentication, boolean isRememberMe) {
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long expiration = appProperties.jwt().expiration();
        long rememberMeTokenExpiration = appProperties.jwt().rememberMeTokenExpiration();
        Instant now = Instant.now();
        Instant expiresAt = isRememberMe
                ? now.plus(rememberMeTokenExpiration, ChronoUnit.MILLIS)
                : now.plus(expiration, ChronoUnit.MILLIS);
        Map<String, String> claims = Map.of(
                AUTHORITIES_CLAIM, authorities
        );
        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(appProperties.jwt().issuer())
                .issuedAt(now)
                .expiresAt(expiresAt)
                .subject(authentication.getName())
                .claims(stringObjectMap -> stringObjectMap.putAll(claims))
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}
