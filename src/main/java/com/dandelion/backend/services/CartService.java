package com.dandelion.backend.services;

import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.payloads.dto.CartDTO;

public interface CartService {

    void addToCart(Long userId, AddToCartBody addToCartBody);

    void removeAnItem(Long userId, Long productId);

    void removeAllItems(Long userId);

    CartDTO getDetailCart(Long userId);
}
