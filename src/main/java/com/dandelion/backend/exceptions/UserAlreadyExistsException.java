package com.dandelion.backend.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException() {
        super("User is already exists");
    }

    public UserAlreadyExistsException(String msg) {
        super(msg);
    }

    public UserAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
