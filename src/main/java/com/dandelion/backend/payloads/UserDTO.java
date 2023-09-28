package com.dandelion.backend.payloads;


import com.dandelion.backend.entities.LocalUser;
import com.dandelion.backend.entities.enumType.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    private String email;

    private String phone;

    private Gender gender;

    private String avatar;

    private Date blocked;

    public UserDTO(LocalUser localUser) {
        this.id = localUser.getId();
        this.fullName = localUser.getFullName();
        this.email = localUser.getEmail();
        this.phone = localUser.getPhone();
        this.gender = localUser.getGender();
        this.avatar = localUser.getAvatar();
        this.blocked = localUser.getBlocked();
    }
}
