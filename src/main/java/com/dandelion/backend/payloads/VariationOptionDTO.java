package com.dandelion.backend.payloads;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VariationOptionDTO {

    private Long id;

    private String value;

    private VariationDTO variation;
}
