package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public class ProductItemBody {
    
    private Long productId;

    private List<String> optionId;

    private String sku;

    @JsonProperty("quantity")
    private Integer quantityInStock;

    private String imgUrl;

    private BigDecimal price;
}
