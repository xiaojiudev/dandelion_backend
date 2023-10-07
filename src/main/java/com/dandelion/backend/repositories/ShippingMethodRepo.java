package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingMethodRepo extends JpaRepository<ShippingMethod, Long> {
}
