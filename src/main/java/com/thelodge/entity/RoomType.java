package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "room_type", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Builder.Default
    @Column(name = "smoke_friendly")
    private Boolean smokeFriendly = false;

    @Builder.Default
    @Column(name = "pet_friendly")
    private Boolean petFriendly = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
