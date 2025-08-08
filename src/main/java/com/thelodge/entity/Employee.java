package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.thelodge.enums.GenderType;

@Entity
@Table(name = "employee", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @Column(length = 12)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(name = "id_proof_type", length = 45)
    private String idProofType;

    @Column(name = "id_proof_no", length = 45)
    private String idProofNo;

    @Column(name = "id_proof_file", length = 255)
    private String idProofFile;

    private LocalDate dob;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")

    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", columnDefinition = "thelodge.gender_enum")
    private GenderType gender;
}
