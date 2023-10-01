package com.dandelion.backend.payloads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name not blank!")
    @Size(min = 5, message = "Name at least 5 characters")
    private String name;

    private String sku;

    private Integer quantity;

    @JsonProperty("img_url")
    private String imgUrl;

    private BigDecimal price;

    @NotBlank(message = "Information not blank!")
    @Size(min = 10, message = "Information at least 10 characters")
    private String description;

    @NotBlank(message = "Information not blank!")
    @Size(min = 10, message = "Information at least 10 characters")
    private String information;
    
    private String category;
}
