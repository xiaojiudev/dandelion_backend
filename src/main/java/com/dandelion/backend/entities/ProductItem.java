package com.dandelion.backend.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku;

    @Column(name = "qty_in_stock", nullable = false)
    private Integer quantityInStock;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifiedAt;


    // Many-to-One with Product table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Product product;

    // Many-to-Many with variation_option - product_configuration
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "product_configuration",
            joinColumns = @JoinColumn(name = "product_item_id"),
            inverseJoinColumns = @JoinColumn(name = "variation_option_id"))
    @ToString.Exclude
    private List<VariationOption> variationOptions = new ArrayList<>();

    // One-to-Many with shopping_cart_item table
    @OneToMany(mappedBy = "productItem", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();

    // One-to-Many with oder_detail
    @OneToMany(mappedBy = "productItem", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<OrderDetail> orderDetails = new ArrayList<>();
}