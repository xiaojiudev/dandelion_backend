package com.dandelion.backend.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException() {
        super("User is already exists");
    }

    public ResourceAlreadyExistsException(String msg) {
        super(msg);
    }

    public ResourceAlreadyExistsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
