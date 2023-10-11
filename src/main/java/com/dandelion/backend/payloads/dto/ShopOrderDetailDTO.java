package com.dandelion.backend.payloads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class ShopOrderDetailDTO {

    private Long id;

    @JsonProperty("product_name")
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
