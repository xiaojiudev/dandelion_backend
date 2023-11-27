package com.dandelion.backend.services;


import com.dandelion.backend.entities.enumType.Order;
import com.dandelion.backend.payloads.OrderRequest;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;

import java.util.List;

public interface OrderService {

    void placeOrder(Long userId, OrderRequest orderRequest);

    ShopOrderDTO updateOrderStatus(Long orderId, Order status);

    List<ShopOrderDTO> getAllOrders();

    List<ShopOrderDTO> getOrdersByUserId(Long userId);
}
