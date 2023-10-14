package com.dandelion.backend.controllers;


import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.payloads.ApiResponse;
import com.dandelion.backend.payloads.dto.CartDTO;
import com.dandelion.backend.services.CartService;
import com.dandelion.backend.utils.CurrentUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class CartController {


    private final CartService cartService;
    private final CurrentUserUtil currentUserUtil;

    @PostMapping("/carts")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartBody addToCartBody) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        cartService.addToCart(userId, addToCartBody);

        return new ResponseEntity<>(new ApiResponse("Item added to the cart successfully", true), HttpStatus.OK);
    }

    @DeleteMapping("/carts/{cartItemId}")
    public ResponseEntity<ApiResponse> removeAnItem(@PathVariable("cartItemId") Long cartItemId) {

        Long userId = currentUserUtil.getCurrentUser().getId();

        cartService.removeAnItem(userId, cartItemId);

        return new ResponseEntity<>(new ApiResponse("Delete item successfully", true), HttpStatus.OK);
    }

    @DeleteMapping("/carts")
    public ResponseEntity<ApiResponse> removeAllItems() {

        Long userId = currentUserUtil.getCurrentUser().getId();

        cartService.removeAllItems(userId);

        return new ResponseEntity<>(new ApiResponse("Delete all items successfully", true), HttpStatus.OK);
    }

    @GetMapping("/carts")
    public ResponseEntity<CartDTO> getDetailCart() {

        Long userId = currentUserUtil.getCurrentUser().getId();

        return new ResponseEntity<>(cartService.getDetailCart(userId), HttpStatus.OK);
    }

}
