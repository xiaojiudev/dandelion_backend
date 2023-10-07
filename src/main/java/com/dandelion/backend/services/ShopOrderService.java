package com.dandelion.backend.services;


import com.dandelion.backend.payloads.ShopOrderBody;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;

public interface ShopOrderService {


    ShopOrderDTO createOrder(Long userId, ShopOrderBody shopOrderBody);

    ShopOrderDTO updateOrder(Long userId, Long shopOrderId);

    ShopOrderDTO getOrderById(Long orderId);

    void deleteOrderById(Long orderId);
}
