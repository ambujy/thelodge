package com.thelodge.service.impl;

import com.thelodge.dto.*;
import com.thelodge.entity.*;
import com.thelodge.repository.*;
import com.thelodge.service.GuestService;
import com.thelodge.util.DtoMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final AddressRepository addressRepository;
    private final HotelRepository hotelRepository;

    @Override
    public GuestResponseDto createGuest(GuestRequestDto dto) {
        AddressDto addressDto = dto.getAddress();

        Address address = Address.builder()
                .line1(addressDto.getLine1())
                .line2(addressDto.getLine2())
                .line3(addressDto.getLine3())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .pincode(addressDto.getPincode())
                .build();

        address = addressRepository.save(address);

        HotelDto hotelDto = dto.getHotel();

        Hotel hotel = hotelRepository.findById(hotelDto.getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Guest guest = Guest.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .idProofType(dto.getIdProofType())
                .idProofNo(dto.getIdProofNo())
                .idProofFile(dto.getIdProofFile())
                .dob(dto.getDob())
                .gender(dto.getGender())
                .address(address)
                .hotel(hotel)
                .createdAt(LocalDate.now())
                .build();

        return DtoMapper.mapToGuestDto(guestRepository.save(guest));
    }

    @Override
    public GuestResponseDto getGuestById(Integer id) {
        return guestRepository.findById(id).map(DtoMapper::mapToGuestDto).orElse(null);
    }

    @Override
    public List<GuestResponseDto> getAllGuests() {
        return guestRepository.findAll()
                .stream()
                .map(DtoMapper::mapToGuestDto)
                .collect(Collectors.toList());
    }

    @Override
    public GuestResponseDto updateGuest(Integer id, GuestRequestDto dto) {

        HotelDto hotelDto = dto.getHotel();

        Hotel hotel = hotelRepository.findById(hotelDto.getId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        AddressDto addressDto = dto.getAddress();

        Address address = addressRepository.findById(addressDto.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        address.setLine1(addressDto.getLine1());
        address.setLine2(addressDto.getLine2());
        address.setLine3(addressDto.getLine3());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setPincode(addressDto.getPincode());
        address = addressRepository.save(address);

        Guest guest = guestRepository.findById(id).orElseThrow();
        guest.setFirstName(dto.getFirstName());
        guest.setLastName(dto.getLastName());
        guest.setPhone(dto.getPhone());
        guest.setEmail(dto.getEmail());
        guest.setIdProofType(dto.getIdProofType());
        guest.setIdProofNo(dto.getIdProofNo());
        guest.setIdProofFile(dto.getIdProofFile());
        guest.setDob(dto.getDob());
        guest.setGender(dto.getGender());
        guest.setUpdatedAt(LocalDate.now());
        guest.setHotel(hotel);
        guest.setAddress(address);

        return DtoMapper.mapToGuestDto(guestRepository.save(guest));
    }

    @Override
    public void deleteGuest(Integer id) {
        guestRepository.deleteById(id);
    }

}
