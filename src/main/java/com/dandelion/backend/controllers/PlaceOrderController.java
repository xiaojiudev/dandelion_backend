package com.dandelion.backend.controllers;

import com.dandelion.backend.payloads.OrderRequest;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;
import com.dandelion.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PlaceOrderController {

    private final OrderService orderService;

    @PostMapping("/place-order")
    public void placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
    }

    @PutMapping("/orders/{orderId}/accept")
    public void acceptOrder(@PathVariable("orderId") Long orderId) {
        orderService.acceptOrder(orderId);
    }

    @PutMapping("/orders/{orderId}/cancel")
    public void cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<ShopOrderDTO>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
    }

}
