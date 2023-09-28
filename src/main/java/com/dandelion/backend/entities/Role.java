package com.dandelion.backend.entities;

import com.dandelion.backend.entities.enumType.RoleBase;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "role")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleBase roleName;


    // Many-to-Many: with User table
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private List<LocalUser> localUsers = new ArrayList<>();


}