package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/{userId}")
    public ResponseEntity<AddToCartBody> addToCart(
            @PathVariable("userId") Long userId,
            @RequestBody AddToCartBody addToCartBody) {
        cartService.addToCart(userId, addToCartBody);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
