package com.dandelion.backend.payloads;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductIdsRequest {
    @JsonProperty("product_ids")
    private List<Long> productIds;
}
