package com.dandelion.backend.entities;

import com.dandelion.backend.entities.enumType.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "password", nullable = false, length = 1000)
    @JsonIgnore
    private String password;

    @Column(name = "full_name", nullable = false, length = 100)
    @JsonProperty("full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "avatar", nullable = false)
    private String avatar;

    @Column(name = "blocked")
    private Date blocked;

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


    // One-to-One: UserAuthentication - user_authentication
    @OneToOne(mappedBy = "user", optional = false, orphanRemoval = true)
    @ToString.Exclude
    private UserAuthentication userAuthentication;

    // Many-to-Many: with Role table - user_role
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    // One-to-Many: with Address table
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    private List<Address> addresses = new ArrayList<>();

    // Many-to-Many with Product table - product_wishlist
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "product_wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    @ToString.Exclude
    @JsonProperty("product_wishlist")
    private List<Product> productsWishlist = new ArrayList<>();

    // One-to-Many with shopping_cart table
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    @JsonProperty("shopping_cart")
    private List<ShoppingCart> shoppingCarts = new ArrayList<>();

    // One-to-Many with shop_oder table
    @OneToMany(mappedBy = "user", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ToString.Exclude
    @JsonProperty("shop_orders")
    private List<ShopOrder> shopOrders = new ArrayList<>();

    // One-to-Many with user_review table
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<UserReview> userReviews = new ArrayList<>();

    @PrePersist
    private void beforeInsert() {
        if (this.gender == null) {
            setGender(Gender.OTHER);
        }

        if (this.avatar == null) {
            setAvatar("https://res.cloudinary.com/de8xbko8y/image/upload/v1703263334/uploads/img-b7b50cc5ad1349aac6bbbfcb0241f711_orxvwt.jpg");
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().toString())).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}