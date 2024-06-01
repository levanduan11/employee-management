package com.coding.core.exception;

public class TimeVerificationOutException extends BadRequestException {
    public TimeVerificationOutException() {
        super("Time verification out");
    }

    public TimeVerificationOutException(String message) {
        super(message);
    }
}
