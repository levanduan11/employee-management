package com.coding.web.request;

import com.coding.core.validation.FieldRequired;
import jakarta.validation.constraints.Email;

public record UpdateProfileRequest(
        @FieldRequired(fieldName = "email")
        @Email
        String email,
        @FieldRequired(fieldName = "First Name")
        String firstName,
        @FieldRequired(fieldName = "Last Name")
        String lastName
) {
}
