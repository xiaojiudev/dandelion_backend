package com.dandelion.backend.entities;

import com.dandelion.backend.entities.enumType.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Status status;

    // One-to-Many with shop_order table
    @OneToMany(mappedBy = "orderStatus", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<ShopOrder> shopOrders = new ArrayList<>();

}