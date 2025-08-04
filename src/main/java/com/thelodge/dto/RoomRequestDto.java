package com.thelodge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequestDto {
    private Integer roomNumber;
    private RoomTypeDto roomType;
    private HotelDto hotel;
}
