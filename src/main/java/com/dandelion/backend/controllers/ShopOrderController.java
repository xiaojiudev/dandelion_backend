package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.ShopOrderBody;
import com.dandelion.backend.payloads.dto.ShopOrderDTO;
import com.dandelion.backend.services.ShopOrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ShopOrderController {

    private final ShopOrderService shopOrderService;

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<ShopOrderDTO> createOrder(
            @PathVariable("userId") Long userId,
            @RequestBody ShopOrderBody shopOrderBody) {
        return new ResponseEntity<>(shopOrderService.createOrder(userId, shopOrderBody), HttpStatus.CREATED);
    }
}
