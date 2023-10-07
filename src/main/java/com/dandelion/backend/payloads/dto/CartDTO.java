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
    @JsonProperty("merchandise_total")
    private BigDecimal merchandiseTotal;
    private List<CartDetailDTO> items;

}
