package com.dandelion.backend.payloads.dto;

import com.dandelion.backend.entities.enumType.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class OrderStatusDTO {
    private Long id;
    private Order status;
}
