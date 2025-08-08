package com.thelodge.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thelodge.enums.BookingStatusType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDto {
    private Integer guestId;
    private Integer employeeId;
    private Integer hotelId;
    private BookingStatusType status;
    private Integer totalRooms;
    private List<Integer> rooms;
    private Integer travelModeId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkOut;

    private BigDecimal totalAmount;
}
