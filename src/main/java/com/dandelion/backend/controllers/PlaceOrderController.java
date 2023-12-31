package com.dandelion.backend.controllers;

import com.dandelion.backend.entities.enumType.Order;
import com.dandelion.backend.payloads.OrderRequest;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;
import com.dandelion.backend.services.OrderService;
import com.dandelion.backend.utils.CurrentUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class PlaceOrderController {

    private final OrderService orderService;
    private final CurrentUserUtil currentUserUtil;

    @PostMapping("/place-order")
    public void placeOrder(@RequestBody OrderRequest orderRequest) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        orderService.placeOrder(userId, orderRequest);
    }

    @PatchMapping("/orders/{orderId}/update/{status}")
    public ResponseEntity<ShopOrderDTO> updateOrderStatus(@PathVariable("orderId") Long orderId, @PathVariable("status") String status) {

        Order orderStatus;

        try {
            orderStatus = Order.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            orderStatus = Order.PROCESSING;
        }

        return new ResponseEntity<>(orderService.updateOrderStatus(orderId, orderStatus), HttpStatus.OK);
    }

    @GetMapping("/orders/all")
    public ResponseEntity<List<ShopOrderDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<ShopOrderDTO>> getOrdersByUserId() {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(orderService.getOrdersByUserId(userId), HttpStatus.OK);
    }

}
