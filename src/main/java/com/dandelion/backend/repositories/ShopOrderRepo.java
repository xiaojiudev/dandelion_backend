package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopOrderRepo extends JpaRepository<ShopOrder, Long> {
}
