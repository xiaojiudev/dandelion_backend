package com.dandelion.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "variation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Variation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    // Many-to-One with product_category table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    @ToString.Exclude
    private ProductCategory productCategory;

    // One-to-Many with variation_option table
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "variation")
    @ToString.Exclude
    private List<VariationOption> variationOptions = new ArrayList<>();
}