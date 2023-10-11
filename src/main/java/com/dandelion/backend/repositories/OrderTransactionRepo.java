package com.dandelion.backend.repositories;

import com.dandelion.backend.entities.OrderTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderTransactionRepo extends JpaRepository<OrderTransaction, Long> {
}
