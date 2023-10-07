package com.dandelion.backend.entities;

import com.dandelion.backend.entities.enumType.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order_status")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Order status;

    // One-to-Many with shop_order table
    @OneToMany(mappedBy = "orderStatus", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<ShopOrder> shopOrders = new ArrayList<>();

}