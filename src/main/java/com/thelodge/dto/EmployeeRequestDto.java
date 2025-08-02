package com.thelodge.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDto {
    private String name;
    private String gender;
    private String phone;
    private String email;
    private Integer hotelId;
    private Integer designationId;
}
