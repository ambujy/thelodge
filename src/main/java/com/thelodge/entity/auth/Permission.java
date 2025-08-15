package com.thelodge.entity.auth;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "permissions", schema = "auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long id;

    @Column(name = "permission_name", unique = true, nullable = false)
    private String permissionName;

    private String description;
}