package com.coding.service;

import org.springframework.security.core.Authentication;

public interface AuthService {

    String generateAccessToken(Authentication authentication, boolean isRememberMe);
}
