package com.dandelion.backend.payloads;


import com.dandelion.backend.entities.enumType.Gender;
import com.dandelion.backend.utils.GenderDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @Email(message = "Email address is not valid!")
    @NotEmpty(message = "Email is not empty!")
    private String email;

    @NotEmpty(message = "Phone is not empty!")
    private String phone;

    @NotEmpty
    @Size(min = 8, max = 32, message = "Password must be min of 8 characters!")
    private String password;

    @NotEmpty
    @JsonProperty("full_name")
    private String fullName;

    @JsonDeserialize(using = GenderDeserializer.class)
    private Gender gender;

    private String avatar;


}
