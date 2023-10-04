package com.dandelion.backend.payloads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private String name;

    private Double weight;

    private Integer quantity;

    @JsonProperty("media_url")
    private String mediaUrl;

    private BigDecimal price;

    private String description;

    private String information;

    private String tag;

    private String category;
}
