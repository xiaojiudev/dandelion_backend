package com.dandelion.backend.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShopOrderBody {

    @JsonProperty("shipping_method_id")
    private Long shippingMethodId;

    @JsonProperty("user_comment")
    private String userComment;

    @JsonProperty("payment_method_id")
    private Long paymentMethodId;
}
