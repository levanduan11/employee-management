package com.coding.domain.enumeration;

import com.coding.domain.model.User;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum ActivationUser {
    USER_NAME("username"),
    VERIFY_URL("verifyUrl");
    private final String key;

    ActivationUser(String key) {
        this.key = key;
    }

    public String getValue() {
        return key;
    }

    public static Map<String, Object> variables(String username, String verifyUrl) {
        return ImmutableMap.of(
                USER_NAME.key, username,
                VERIFY_URL.key, verifyUrl
        );
    }
}
