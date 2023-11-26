package com.dandelion.backend.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BearerToken {

    private String name;
    private String email;
    private String image;
    private String accessToken;
    private String tokenType;
    private List<String> roles;

}
