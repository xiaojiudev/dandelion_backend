package com.dandelion.backend.controllers;

import com.dandelion.backend.payloads.BearerToken;
import com.dandelion.backend.payloads.LoginRequest;
import com.dandelion.backend.payloads.RegistrationRequest;
import com.dandelion.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {

        return userService.register(registrationRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<BearerToken> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.authenticate(loginRequest), HttpStatus.OK);
    }
}
