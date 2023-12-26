package com.dandelion.backend.payloads.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartDetailDTO {

    @JsonProperty("product_id")
    private Long productId;
    private String name;
    private Integer quantity;
    @JsonProperty("available_quantity")
    private Integer availableQuantity;
    @JsonProperty("media_url")
    private String mediaUrl;
    @JsonProperty("unit_price")
    private BigDecimal unitPrice;
    @JsonProperty("item_sub_total")
    private BigDecimal itemSubTotal;
    private String description;
    private String information;
}
