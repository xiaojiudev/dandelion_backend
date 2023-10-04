package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<ShoppingCartItem, Long> {
}
