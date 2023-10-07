package com.dandelion.backend.services.impl;

import com.dandelion.backend.entities.OrderStatus;
import com.dandelion.backend.exceptions.ResourceNotFoundException;
import com.dandelion.backend.payloads.dto.OrderStatusDTO;
import com.dandelion.backend.repositories.OrderStatusRepo;
import com.dandelion.backend.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {

    private final OrderStatusRepo orderStatusRepo;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderStatusDTO> getAllOrderStatuses() {
        List<OrderStatus> orderStatusStatuses = orderStatusRepo.findAll();

        List<OrderStatusDTO> orderStatusDTOList = orderStatusStatuses.stream()
                .map(item -> modelMapper.map(item, OrderStatusDTO.class))
                .collect(Collectors.toList());

        return orderStatusDTOList;
    }

    @Override
    public OrderStatusDTO getOrderStatus(Long statusId) {
        OrderStatus orderStatus = orderStatusRepo.findById(statusId)
                .orElseThrow(() -> new ResourceNotFoundException("Order status not found!"));
        return modelMapper.map(orderStatus, OrderStatusDTO.class);
    }
}
