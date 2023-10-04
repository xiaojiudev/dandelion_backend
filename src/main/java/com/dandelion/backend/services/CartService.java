package com.dandelion.backend.services;

import com.dandelion.backend.payloads.AddToCartBody;

public interface CartService {

    public void addToCart(Long userId, AddToCartBody addToCartBody);
}
