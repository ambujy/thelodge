package com.thelodge.service;

import com.thelodge.dto.GuestRequestDto;
import com.thelodge.dto.GuestResponseDto;

import java.util.List;

public interface GuestService {
    GuestResponseDto createGuest(GuestRequestDto guestRequestDto);
    GuestResponseDto getGuestById(Integer id);
    List<GuestResponseDto> getAllGuests();
    GuestResponseDto updateGuest(Integer id, GuestRequestDto guestRequestDto);
    void deleteGuest(Integer id);
}
