package com.dandelion.backend.payloads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

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
    private List<String> mediaUrl;

    private BigDecimal price;

    private String description;

    private String information;

    private String tag;

    private String category;
}
