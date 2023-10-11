package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequest {

    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("shipping_method_id")
    Long shippingMethodId;

    @JsonProperty("payment_method_id")
    Long paymentMethodId;
}
