package com.thelodge.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Integer id;

    private String firstName;
    private String lastName;
    private String phone;
    private String email;

}
