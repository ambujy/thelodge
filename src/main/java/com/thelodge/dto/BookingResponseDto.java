package com.thelodge.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDto {
    private Integer id;
    private GuestDto guest;
    private EmployeeDto employee;
    private HotelDto hotel;
    private String status;
    private Integer totalRooms;
    private List<RoomResponseDto> rooms;
    private TravelModeDto travelMode;
    private LocalDateTime bookingDate;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
