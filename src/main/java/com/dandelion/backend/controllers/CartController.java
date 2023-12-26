package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.ProductIdsRequest;
import com.dandelion.backend.payloads.dto.CartDTO;
import com.dandelion.backend.services.CartService;
import com.dandelion.backend.utils.CurrentUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CartController {


    private final CartService cartService;
    private final CurrentUserUtil currentUserUtil;

    @PostMapping("/carts")
    public ResponseEntity<CartDTO> addToCart(@RequestBody AddToCartBody addToCartBody) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(cartService.addToCart(userId, addToCartBody), HttpStatus.OK);

    }

    @DeleteMapping("/carts")
    public ResponseEntity<ApiResponse> removeItems(@RequestBody ProductIdsRequest request) {

        Long userId = currentUserUtil.getCurrentUser().getId();
        List<Long> productIds = request.getProductIds();

        cartService.removeItems(userId, productIds);

        return new ResponseEntity<>(new ApiResponse("Delete items successfully", true), HttpStatus.OK);
    }

    @GetMapping("/carts")
    public ResponseEntity<CartDTO> getDetailCart() {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(cartService.getDetailCart(userId), HttpStatus.OK);
    }

}
