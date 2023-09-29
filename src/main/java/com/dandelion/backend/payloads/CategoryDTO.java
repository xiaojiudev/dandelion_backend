package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {

    private Long id;

    @NotBlank(message = "Category name is not blank!")
    @Size(min = 1, max = 100, message = "Category name at least 1 characters")
    @JsonProperty(namespace = "name")
    private String name;
}
