package com.dandelion.backend.payloads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ShopOrderDTO {
    private Long id;

    @JsonProperty("user_comment")
    private String userComment;
    @JsonProperty("user_phone")
    private String userPhone;

    @JsonProperty("shipping_address")
    private String shippingAddress;

    @JsonProperty("merchandise_total")
    private BigDecimal merchandiseTotal;

    @JsonProperty("shipping_fee")
    private BigDecimal shippingFee;
    private BigDecimal total;
    @JsonProperty("tracking_code")
    private String trackingCode;

}
