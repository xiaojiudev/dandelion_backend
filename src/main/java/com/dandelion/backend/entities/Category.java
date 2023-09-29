package com.dandelion.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "product_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    // One-to-Many with product table
    @OneToMany(mappedBy = "productCategory")
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();

    // One-to-Many with variation table
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productCategory")
    @ToString.Exclude
    private List<Variation> variations = new ArrayList<>();
}