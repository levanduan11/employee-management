package com.coding.web.request;

import com.coding.core.validation.FieldRequired;

public record LoginRequest(
        @FieldRequired(fieldName = "username")
        String username,
        @FieldRequired(fieldName = "password")
        String password,
        boolean rememberMe
) {
}
