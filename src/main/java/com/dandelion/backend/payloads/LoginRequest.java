package com.dandelion.backend.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
