package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Long id;

    @JsonProperty("address_line_1")
    private String addressLine1;

    @JsonProperty("address_line_2")
    private String addressLine2;

    private String phone;

    private String city;

    private String district;

    private String ward;

    private String country;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("is_default")
    private Boolean isDefault;
}
