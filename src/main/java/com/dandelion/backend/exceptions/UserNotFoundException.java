package com.dandelion.backend.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
