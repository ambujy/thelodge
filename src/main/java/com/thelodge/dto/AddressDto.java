package com.thelodge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private Integer id;
    private String line1;
    private String line2;
    private String line3;
    private String city;
    private String state;
    private String pincode;
}
