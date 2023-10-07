package com.dandelion.backend.controllers;

import com.dandelion.backend.payloads.dto.OrderStatusDTO;
import com.dandelion.backend.services.OrderStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping("/order-status")
    public ResponseEntity<List<OrderStatusDTO>> getAllOrderStatuses() {

        return new ResponseEntity<>(orderStatusService.getAllOrderStatuses(), HttpStatus.OK);
    }

    @GetMapping("/order-status/{statusId}")
    public ResponseEntity<OrderStatusDTO> getOrderStatus(@PathVariable("statusId") Long statusId) {

        return new ResponseEntity<>(orderStatusService.getOrderStatus(statusId), HttpStatus.OK);
    }
}
