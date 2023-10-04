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

    private Long productId;
    private String name;
    private Integer quantity;
    @JsonProperty("media_url")
    private String mediaUrl;
    private BigDecimal price;
    private String description;
    private String information;
}
