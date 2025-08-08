package com.thelodge.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import com.thelodge.enums.BookingStatusType;

@Entity
@Table(name = "booking_status", schema = "thelodge")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatusType status;

    @Column(name = "changed_at")
    private Timestamp changedAt;

    @Column(name = "changed_by")
    private String changedBy;
}
