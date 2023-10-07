package com.dandelion.backend.services;

import com.dandelion.backend.payloads.dto.OrderStatusDTO;

import java.util.List;

public interface OrderStatusService {

    List<OrderStatusDTO> getAllOrderStatuses();

    OrderStatusDTO getOrderStatus(Long statusId);
}
