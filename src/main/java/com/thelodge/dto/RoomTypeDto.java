package com.thelodge.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomTypeDto {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal cost;
    private Boolean smokeFriendly;
    private Boolean petFriendly;
}
