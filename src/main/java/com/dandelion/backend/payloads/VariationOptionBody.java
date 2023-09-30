package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VariationOptionBody {

    private Long id;

    @JsonProperty("variation_id")
    private Long variationId;

    private String value;
}
