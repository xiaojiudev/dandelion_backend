package com.dandelion.backend.services;

import com.dandelion.backend.payloads.AddToCartBody;
import com.dandelion.backend.payloads.dto.CartDTO;

import java.util.List;

public interface CartService {

    CartDTO addToCart(Long userId, AddToCartBody addToCartBody);

    void removeItems(Long userId, List<Long> productIds);

    CartDTO getDetailCart(Long userId);
}
