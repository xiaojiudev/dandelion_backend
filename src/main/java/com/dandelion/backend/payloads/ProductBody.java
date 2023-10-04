package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProductBody {

    @NotBlank(message = "Name not blank!")
    @Size(min = 5, message = "Name at least 5 characters")
    private String name;

    private Double weight;

    private Integer quantity;

    @JsonProperty("media_url")
    private String mediaUrl;

    private BigDecimal price;

    @NotBlank(message = "Information not blank!")
    @Size(min = 10, message = "Information at least 10 characters")
    private String description;

    @NotBlank(message = "Information not blank!")
    @Size(min = 10, message = "Information at least 10 characters")
    private String information;

    @Size(max = 500, message = "Tag max with 500 characters")
    private String tag;

    @JsonProperty("category_id")
    @NotNull(message = "Category ID not null!")
    private Long categoryId;

}
