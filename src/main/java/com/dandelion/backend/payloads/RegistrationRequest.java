package com.dandelion.backend.payloads;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b")
    private String phone;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 32)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")
    private String password;

    @NotNull
    @NotBlank
    @JsonProperty("full_name")
    private String fullName;
}
