package com.thelodge.dto;

import com.thelodge.enums.GenderType;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String idProofType;
    private String idProofNo;
    private String idProofFile;
    private LocalDate dob;
    private GenderType gender;
    private AddressDto address;
    private HotelDto hotel;
}
