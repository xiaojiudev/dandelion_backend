package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.ShoppingCartItem;
import com.dandelion.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepo extends JpaRepository<ShoppingCartItem, Long> {
    Optional<ShoppingCartItem> findByShoppingCart_IdAndProduct_Id(Long cartId, Long productId);

    List<ShoppingCartItem> findByShoppingCart_Id(Long id);

    List<ShoppingCartItem> findByShoppingCart_StatusAndShoppingCart_User(Boolean status, User user);

    List<ShoppingCartItem> findByProduct_Id(Long id);


}
