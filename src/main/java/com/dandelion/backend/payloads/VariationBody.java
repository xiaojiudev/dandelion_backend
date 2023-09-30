package com.dandelion.backend.payloads;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VariationDTO {

    private Long id;

    private CategoryDTO category;

    private String name;

}
