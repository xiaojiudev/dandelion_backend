package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.OrderStatus;
import com.dandelion.backend.entities.enumType.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepo extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByStatus(Order status);

    
}
