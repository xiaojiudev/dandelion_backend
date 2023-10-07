package com.dandelion.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "shop_order")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_comment")
    @JsonProperty("user_comment")
    private String userComment;

    @Column(name = "user_phone")
    @JsonProperty("user_phone")
    private String userPhone;

    @Column(name = "shipping_address", nullable = false)
    @JsonProperty("shipping_address")
    private String shippingAddress;

    @Column(name = "merchandise_total", nullable = false, precision = 10, scale = 2)
    @JsonProperty("merchandise_total")
    private BigDecimal merchandiseTotal;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "tracking_code", nullable = false, unique = true, length = 50)
    @JsonProperty("tracking_code")
    private String trackingCode;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifiedAt;

    // TODO: Define Relationship with ShippingMethod, OrderStatus, OrderDetail

    // Many-to-One with user table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    // Many-to-One with shipping_method table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "shipping_method_id", nullable = false)
    @ToString.Exclude
    private ShippingMethod shippingMethod;

    // Many-to-One with order_status table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "order_status_id", nullable = false)
    @ToString.Exclude
    private OrderStatus orderStatus;

    // One-to-Many with order_detail table
    @OneToMany(mappedBy = "shopOrder", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<OrderDetail> orderDetails = new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_transaction_id")
    private OrderTransaction orderTransaction;

}