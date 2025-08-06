package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_method", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
}
