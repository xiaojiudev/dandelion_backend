package com.dandelion.backend.exceptions;

public class UserNotAuthenticatedException extends RuntimeException {

    public UserNotAuthenticatedException(String msg) {
        super(msg);
    }


}

