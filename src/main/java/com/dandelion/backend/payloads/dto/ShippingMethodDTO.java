package com.dandelion.backend.payloads.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShippingMethodDTO {
    private Long id;
    private String name;
    private BigDecimal price;

}
