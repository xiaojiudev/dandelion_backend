package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddToCartBody {

    @JsonProperty("product_id")
    private Long productId;

    private Integer quantity;
}
