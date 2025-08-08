package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "hotel", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String contact1;
    private String contact2;
    private String contact3;
    private String email;
    private String website;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "room_capacity", nullable = false)
    private Integer roomCapacity;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp deletedAt;
}
