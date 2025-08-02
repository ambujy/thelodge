package com.thelodge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelResponseDto {

    private Integer id;
    private String name;
    private String contact1;
    private String contact2;
    private String contact3;
    private String email;
    private String website;
    private String description;
    private Integer roomCapacity;
    private AddressDto address;
}
