package com.thelodge.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDto {
    private Integer id;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private Integer hotelId;
    private String hotelName;
    private Integer designationId;
    private String designationTitle;
}

