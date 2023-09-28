package com.dandelion.backend.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Custom Exception Handler

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardError> userAlreadyExistsExceptionHandler(UserAlreadyExistsException e, HttpServletRequest request) {
        StandardError err = StandardError.builder()
                .timeStamp(new Date())
                .status(HttpStatus.CONFLICT.value())
                .error("User is already exists")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardError> userNotFoundExceptionHandler(UserNotFoundException e, HttpServletRequest request) {
        StandardError err = StandardError.builder()
                .timeStamp(new Date())
                .status(HttpStatus.NOT_FOUND.value())
                .error("User not found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
