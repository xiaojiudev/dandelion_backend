package com.dandelion.backend.payloads.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartDTO {
    @JsonProperty("cart_id")
    private Long cartId;
    @JsonProperty("user_id")
    private Long userId;
    private Boolean status;
    @JsonProperty("merchandise_total")
    private BigDecimal merchandiseTotal;
    private BigDecimal shippingFee;
    private List<CartDetailDTO> items;

}
