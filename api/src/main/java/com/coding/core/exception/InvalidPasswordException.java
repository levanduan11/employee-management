package com.coding.core.exception;

public class InvalidPasswordException extends BadRequestException{

    public InvalidPasswordException() {
        super("Incorrect password");
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
