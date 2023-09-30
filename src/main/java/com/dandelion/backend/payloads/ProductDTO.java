package com.dandelion.backend.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name not blank!")
    @Size(min = 5, message = "Name at least 5 characters")
    private String name;

    @NotBlank(message = "Information not blank!")
    @Size(min = 10, message = "Information at least 10 characters")
    private String description;

    @NotBlank(message = "Information not blank!")
    @Size(min = 10, message = "Information at least 10 characters")
    private String information;
    
    private CategoryDTO category;

}
