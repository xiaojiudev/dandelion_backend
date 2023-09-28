package com.dandelion.backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "promotion")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_code", unique = true, nullable = false, length = 20)
    private String nameCode;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "discount_rate", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "100.00")
    private BigDecimal discountRate;

    @Column(name = "max_price_discount", precision = 10, scale = 2, nullable = false)
    @DecimalMin(value = "0.00")
    private BigDecimal maxPriceDiscount;

    @Column(name = "start_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    // Many-to-Manny with Product table - promotion_product
    @ManyToMany(mappedBy = "promotions")
    @ToString.Exclude
    private List<Product> products = new ArrayList<>();
}
