package com.thelodge.entity.auth;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "auth") // Matches your schema table name
@Data // Lombok for getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") // Matches your schema column name
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // Many-to-Many relationship with Role through user_roles table
    @ManyToMany(fetch = FetchType.EAGER) // Fetch roles eagerly when user is loaded
    @JoinTable(
        name = "user_roles", // Junction table name
        schema = "auth", // Matches your schema
        joinColumns = @JoinColumn(name = "user_id"), // Column in user_roles linking to users table
        inverseJoinColumns = @JoinColumn(name = "role_id") // Column in user_roles linking to roles table
    )
    private Set<Role> roles = new HashSet<>();

    // Constructor for creating new users (passwordHash will be set by service)
    public Users(String username, String passwordHash, boolean isActive) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.isActive = isActive;
        this.createdAt = LocalDateTime.now();
    }
}
