package com.dandelion.backend.payloads;

import com.dandelion.backend.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDTO extends UserDTO {

    private List<Address> address = new ArrayList<>();


//    public UserDetailDTO(User user) {
//        super(user);
//        this.address = user.getAddresses();
//    }
}
