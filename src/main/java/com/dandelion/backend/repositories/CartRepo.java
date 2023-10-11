package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.ShoppingCart;
import com.dandelion.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByStatusAndUser_Id(Boolean status, Long id);

    ShoppingCart findByStatusAndUser(Boolean status, User user);

}
