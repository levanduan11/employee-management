package com.coding.core.exception;

public class EmailAlreadyUsedException extends BadRequestException {
    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }

    public EmailAlreadyUsedException(String email) {
        super("Email is already in use: " + email);
    }
}
