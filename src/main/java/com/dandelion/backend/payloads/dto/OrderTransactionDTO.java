package com.dandelion.backend.payloads.dto;

import com.dandelion.backend.entities.enumType.TransactionStatus;

import java.math.BigDecimal;

public class OrderTransactionDTO {
    private Long id;
    private TransactionStatus transactionStatus;
    private BigDecimal transactionAmount;
}
