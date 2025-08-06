package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "travel_mode", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
