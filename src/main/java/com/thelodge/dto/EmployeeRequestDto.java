package com.thelodge.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String idProofType;
    private String idProofNo;
    private String idProofFile;
    private LocalDate dob;
    private String gender;

    private AddressDto address;
    private DesignationDto designation;
    private HotelDto hotel;
}
