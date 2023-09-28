package com.dandelion.backend.payloads;

import com.dandelion.backend.entities.Address;
import com.dandelion.backend.entities.LocalUser;
import com.dandelion.backend.entities.enumType.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO extends UserDTO {

    private List<Address> address = new ArrayList<>();


    public UserDetailDTO(LocalUser localUser) {
        super(localUser);
        this.address = localUser.getAddresses();
    }
}
