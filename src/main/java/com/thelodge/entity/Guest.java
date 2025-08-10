package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.thelodge.enums.GenderType;

@Entity
@Table(name = "guest", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String phone;

    private String email;

    @Column(name = "id_proof_type")
    private String idProofType;

    @Column(name = "id_proof_no")
    private String idProofNo;

    @Column(name = "id_proof_file")
    private String idProofFile;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "thelodge.gender_enum")
    private GenderType gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
