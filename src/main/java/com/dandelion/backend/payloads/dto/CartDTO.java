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

    private Long userId;
    private Boolean status;
    @JsonProperty("merchandise_subtotal")
    private BigDecimal merchandiseSubtotal;
    @JsonProperty("shipping_total")
    private BigDecimal shippingTotal;
    @JsonProperty("total_payment")
    private BigDecimal totalPayment;
    private List<CartDetailDTO> items;

}
