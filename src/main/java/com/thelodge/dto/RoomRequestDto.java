package com.thelodge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomRequestDto {
    private Integer roomNumber;
    private Integer roomTypeId;
    private HotelDto hotel;
}
