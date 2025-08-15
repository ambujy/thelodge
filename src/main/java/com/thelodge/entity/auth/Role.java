package com.thelodge.entity.auth;

import lombok.*;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles", schema = "auth") // Matches your schema table name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id") // Matches your schema column name
    private Long id;

    @Column(name = "role_name", unique = true, nullable = false)
    private String roleName;

    private String description;

    // Many-to-Many relationship with Permission through role_permissions table
    @ManyToMany(fetch = FetchType.EAGER) // Fetch permissions eagerly when role is loaded
    @JoinTable(
        name = "role_permissions", // Junction table name
        schema = "auth", // Matches your schema
        joinColumns = @JoinColumn(name = "role_id"), // Column in role_permissions linking to roles table
        inverseJoinColumns = @JoinColumn(name = "permission_id") // Column in role_permissions linking to permissions table
    )
    private Set<Permission> permissions = new HashSet<>();
}
