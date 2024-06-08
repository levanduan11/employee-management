package com.coding.web.request

import com.coding.core.validation.FieldRequired

data class UpdatePasswordRequest(
    @field:FieldRequired(fieldName = "Old Password")
    val oldPassword: String,

    @field:FieldRequired(fieldName = "New Password")
    val newPassword: String
)
