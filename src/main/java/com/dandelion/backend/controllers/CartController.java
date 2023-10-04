package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.dto.CartDTO;
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
    public ResponseEntity<ApiResponse> addToCart(
            @PathVariable("userId") Long userId,
            @RequestBody AddToCartBody addToCartBody) {
        cartService.addToCart(userId, addToCartBody);

        return new ResponseEntity<>(new ApiResponse("Item added to the cart successfully", true), HttpStatus.OK);
    }

    @DeleteMapping("/cart/{userId}/remove/{cartItemId}")
    public ResponseEntity<ApiResponse> removeAnItem(
            @PathVariable("userId") Long userId,
            @PathVariable("cartItemId") Long cartItemId) {
        cartService.removeAnItem(userId, cartItemId);

        return new ResponseEntity<>(new ApiResponse("Delete item successfully", true), HttpStatus.OK);
    }

    @DeleteMapping("/cart/{userId}/remove")
    public ResponseEntity<ApiResponse> removeAllItems(@PathVariable("userId") Long userId) {
        cartService.removeAllItems(userId);

        return new ResponseEntity<>(new ApiResponse("Delete all items successfully", true), HttpStatus.OK);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<CartDTO> getDetailCart(@PathVariable("userId") Long userId) {

        return new ResponseEntity<>(cartService.getDetailCart(userId), HttpStatus.OK);
    }
}
