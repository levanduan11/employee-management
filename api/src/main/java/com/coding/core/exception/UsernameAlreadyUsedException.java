package com.coding.core.exception;

public class UsernameAlreadyUsedException extends BadRequestException {
    public UsernameAlreadyUsedException() {
        super("Login name already used!");
    }

    public UsernameAlreadyUsedException(String username) {
        super(" username already used: " + username);
    }
}
