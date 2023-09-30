package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VariationBody {

    private Long id;

    @JsonProperty("category_id")
    private Long categoryId;

    private String name;

}
