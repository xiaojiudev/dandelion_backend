package com.dandelion.backend.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Custom Exception Handler

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<StandardError> userAlreadyExistsExceptionHandler(ResourceAlreadyExistsException e, HttpServletRequest request) {
        StandardError err = StandardError.builder()
                .timeStamp(new Date())
                .status(HttpStatus.CONFLICT.value())
                .error("User is already exists")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> userNotFoundExceptionHandler(ResourceNotFoundException e, HttpServletRequest request) {
        StandardError err = StandardError.builder()
                .timeStamp(new Date())
                .status(HttpStatus.NOT_FOUND.value())
                .error("User not found")
                .message(e.getMessage())
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> res = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((err) -> {
            String fieldName = ((FieldError) err).getField();
            String message = err.getDefaultMessage();

            res.put(fieldName, message);
        });

        return new ResponseEntity<Map<String, String>>(res, HttpStatus.BAD_REQUEST);
    }
}
