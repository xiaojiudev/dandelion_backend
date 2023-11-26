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
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "weight", nullable = false, precision = 10, scale = 2)
    private Double weight;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "media_url", nullable = false)
    @Lob
    private String mediaUrl;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "description", nullable = false)
    @Lob
    private String description;

    @Column(name = "information", nullable = false)
    @Lob
    private String information;

    @Column(name = "tag", length = 500)
    private String tag;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifiedAt;

    // Many-to-Many with User table - product_wishlist
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "productsWishlist")
    @ToString.Exclude
    private List<User> users = new ArrayList<>();

    // Many-to-Many with Promotion table - promotion_product
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "promotion_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id"))
    @ToString.Exclude
    private List<Promotion> promotions = new ArrayList<>();

    // Many-to-One with category table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private Category category;


    // One-to-Many with oder_detail
    @OneToMany(mappedBy = "product", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // One-to-Many with shopping_cart_item table
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<ShoppingCartItem> shoppingCartItems = new ArrayList<>();
}