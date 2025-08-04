package com.thelodge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponseDto {
    private Integer id;
    private Integer roomNumber;
    private RoomTypeDto roomType;
    private HotelDto hotel;
}
