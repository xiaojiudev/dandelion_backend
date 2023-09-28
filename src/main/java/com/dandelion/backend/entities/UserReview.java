package com.dandelion.backend.entities;

import com.dandelion.backend.entities.enumType.Rating;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Entity
@Table(name = "user_review")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rating_value", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Rating ratingValue;

    @Column(name = "comment", length = 300)
    private String comment;

    @Column(name = "like")
    private Integer like;

    @Lob
    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date modifiedAt;

    @PrePersist
    private void beforeInsert() {
        if (like == null) {
            setLike(0);
        }
    }

    // Many-to-One with order-detail table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "ordered_product_id")
    @ToString.Exclude
    private OrderDetail orderDetail;

    // Many-to-One with user table
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private LocalUser localUser;
}