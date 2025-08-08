package com.thelodge.dto;

import lombok.*;
import java.time.LocalDateTime;

import com.thelodge.enums.BookingStatusType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingStatusDto {
    private Integer id;
    private Integer bookingId;
    private BookingStatusType status;
    private String changedBy;
    private LocalDateTime changedAt;
}
