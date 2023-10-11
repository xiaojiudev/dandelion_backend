package com.dandelion.backend.services;


import com.dandelion.backend.payloads.OrderRequest;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;

import java.util.List;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);

    void acceptOrder(Long orderId);

    void cancelOrder(Long orderId);

    List<ShopOrderDTO> getAllOrders();
}
