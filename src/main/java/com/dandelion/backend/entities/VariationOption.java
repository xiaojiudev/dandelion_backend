package com.dandelion.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "variation_option")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class VariationOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", nullable = false)
    private String value;

    // TODO: Define Relationship with ProductItem, Variation

    // Many-to-One with variation table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "variation_id")
    @ToString.Exclude
    private Variation variation;

    // Many-to-Many with product_item - product_configuration
    @ManyToMany(mappedBy = "variationOptions")
    @ToString.Exclude
    private List<ProductItem> productItems = new ArrayList<>();
}