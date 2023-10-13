package com.dandelion.backend.payloads;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BearerToken {

    private String accessToken;
    private String tokenType;

}
