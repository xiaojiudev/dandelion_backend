package com.dandelion.backend.payloads;


import com.dandelion.backend.entities.enumType.Gender;
import com.dandelion.backend.utils.GenderDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;

    @Email(message = "Email address is not valid!")
    @NotBlank(message = "Email is not blank!")
    private String email;

    @NotBlank(message = "Phone is not blank!")
    @Size(min = 10, max = 10, message = "Phone must be with 10 characters!")
    private String phone;

    @NotBlank(message = "Password is not blank!")
    @Size(min = 8, max = 32, message = "Password must be min of 8 characters!")
    private String password;

    @NotBlank(message = "Name is not blank!")
    @Size(min = 2, max = 100, message = "Name must be in range of 2-100 characters!")
    @JsonProperty("full_name")
    private String fullName;

    @JsonDeserialize(using = GenderDeserializer.class)
    private Gender gender;

    private String avatar;


}
