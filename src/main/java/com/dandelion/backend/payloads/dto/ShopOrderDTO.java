package com.dandelion.backend.payloads.dto;

import com.dandelion.backend.entities.enumType.Order;
import com.dandelion.backend.entities.enumType.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ShopOrderDTO {

    private Long id;

    @JsonProperty("user_full_name")
    private String userFullName;

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

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("order_status")
    private Order orderStatus;

    @JsonProperty("transaction_status")
    private TransactionStatus transactionStatus;

    @JsonProperty("tracking_code")
    private String trackingCode;

    @JsonProperty("order_details")
    List<ShopOrderDetailDTO> orderDetails;
}
