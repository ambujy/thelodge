package com.thelodge.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
    private Integer id;

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

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
