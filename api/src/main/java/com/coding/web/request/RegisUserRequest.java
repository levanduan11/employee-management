package com.coding.web.request;

import com.coding.core.validation.FieldRequired;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisUserRequest(
        @FieldRequired(fieldName = "username")
        String username,
        @FieldRequired(fieldName = "email")
        @Email
        String email,
        @FieldRequired(fieldName = "First Name")
        String firstName,
        @FieldRequired(fieldName = "Last Name")
        String lastName,
        @Size(min = 1)
        Set<String> roles
) {
}
