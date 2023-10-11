package com.dandelion.backend.entities;

import com.dandelion.backend.entities.enumType.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "order_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "transaction_status")
    @JsonProperty("transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "transaction_amount", nullable = false, precision = 10, scale = 2)
    @JsonProperty("transaction_amount")
    private BigDecimal transactionAmount;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifiedAt;


    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @ToString.Exclude
    @OneToOne(mappedBy = "orderTransaction", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, optional = false, orphanRemoval = true)
    private ShopOrder shopOrder;

}
