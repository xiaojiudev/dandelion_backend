package com.dandelion.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "order_date", nullable = false)
    @JsonProperty("order_date")
    private Date orderDate;

    @Column(name = "payment_method", nullable = false, length = 50)
    @JsonProperty("payment_method")
    private String paymentMethod;

    @Column(name = "shipping_address", nullable = false)
    @JsonProperty("shipping_address")
    private String shippingAddress;

    @Column(name = "order_user_fullname", nullable = false, length = 50)
    @JsonProperty("order_user_fullname")
    private String orderUserFullname;

    @Column(name = "order_user_phone", nullable = false, length = 20)
    @JsonProperty("order_user_phone")
    private String orderUserPhone;

    @Column(name = "order_user_email", nullable = false)
    @JsonProperty("order_user_email")
    private String orderUserEmail;

    @Column(name = "order_total", nullable = false, precision = 10, scale = 2)
    @JsonProperty("order_total")
    private BigDecimal orderTotal;

    @Column(name = "order_tracking_number", nullable = false, unique = true, length = 50)
    @JsonProperty("order_tracking_number")
    private String orderTrackingNumber;

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

}