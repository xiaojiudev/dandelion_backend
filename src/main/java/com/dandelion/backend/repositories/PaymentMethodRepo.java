package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByNameIgnoreCase(String name);
    
}
