package com.dandelion.backend.controllers;

import com.dandelion.backend.payloads.OrderRequest;
import com.dandelion.backend.payloads.PaymentMethodRequest;
import com.dandelion.backend.payloads.ShippingMethodRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CheckOutController {

    @PostMapping("/choose-shipping")
    public ResponseEntity<?> chooseShippingMethod(@RequestBody ShippingMethodRequest request) {
        // Implement the logic to choose a shipping method
        // Use userService and addressService to access user and address information
        // Return an appropriate response

        return null;
    }

    @PostMapping("/choose-payment")
    public ResponseEntity<?> choosePaymentMethod(@RequestBody PaymentMethodRequest request) {
        // Implement the logic to choose a payment method
        // Use userService to access user information
        // Return an appropriate response
        return null;
    }

    @PostMapping("/place-order")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest request) {
        // Implement the logic to place an order
        // Use userService and addressService to access user and address information
        // Return an appropriate response
        return null;
    }
}
