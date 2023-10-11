package com.dandelion.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order_detail")
@ToString
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // TODO: Define Relationship with ProductItem, ShopOrder

    // Many-to-One with product table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    // Many-to-One with shop_order table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "shop_order_id", nullable = false)
    @ToString.Exclude
    private ShopOrder shopOrder;

    // One-to-Many with user_review
    @OneToMany(mappedBy = "orderDetail", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<UserReview> userReviews = new ArrayList<>();

}