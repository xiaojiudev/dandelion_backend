package com.dandelion.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address_line_1")
    @JsonProperty("address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    @JsonProperty("address_line_2")
    private String addressLine2;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "city")
    private String city;

    @Column(name = "district")
    private String district;

    @Column(name = "ward")
    private String ward;

    @Column(name = "country", columnDefinition = "varchar(255) default 'Viet Nam'", updatable = false)
    private String country;

    @Column(name = "postal_code", length = 20)
    @JsonProperty("postal_code")
    private String postalCode;

    @Column(name = "is_default")
    @JsonProperty("is_default")
    private Boolean isDefault;

    @Column(name = "created_at", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @JsonProperty("created_at")
    private Date createdAt;

    @Column(name = "modified_at")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @JsonProperty("modified_at")
    private Date modifiedAt;

    // TODO: Define Relationship with Country, LocalUser
    // Many-to-One: with User table

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;


}
