package com.dandelion.backend.payloads.dto;


import com.dandelion.backend.entities.enumType.Gender;
import com.dandelion.backend.entities.enumType.RoleBase;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;

    private String email;

    private String phone;

    private String password;

    @JsonProperty("full_name")
    private String fullName;

    private Gender gender;

    private String avatar;

    private Date blocked;

    private List<RoleBase> roles;
}
