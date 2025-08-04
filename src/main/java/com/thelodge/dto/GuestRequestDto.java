package com.thelodge.dto;

import com.thelodge.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestRequestDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String idProofType;
    private String idProofNo;
    private String idProofFile;
    private LocalDate dob;
    private Gender gender;
    private AddressDto address;
    private HotelDto hotel;
}
