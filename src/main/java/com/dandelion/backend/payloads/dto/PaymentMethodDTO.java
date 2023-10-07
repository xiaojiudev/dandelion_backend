package com.dandelion.backend.payloads.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PaymentMethodDTO {
    private Long id;
    private String name;
    @JsonProperty("is_enabled")
    private Boolean isEnabled;

}
