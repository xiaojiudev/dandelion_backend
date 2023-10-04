package com.dandelion.backend.services;

import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.payloads.dto.CartDTO;

public interface CartService {

    public void addToCart(Long userId, AddToCartBody addToCartBody);

    public void removeAnItem(Long userId, Long productId);

    public void removeAllItems(Long userId);

    public CartDTO getDetailCart(Long userId);
}
