package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "address", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String state;
    private String pincode;

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
