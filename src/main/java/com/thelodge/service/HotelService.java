package com.thelodge.service;

import java.util.List;

import com.thelodge.dto.HotelRequestDto;
import com.thelodge.dto.HotelResponseDto;

public interface HotelService {

    HotelResponseDto createHotel(HotelRequestDto hotelRequestDto);
    List<HotelResponseDto> getAllHotels();
    HotelResponseDto getHotelById(Integer id);
    HotelResponseDto updateHotel(Integer id, HotelRequestDto hotelRequestDto);
    void deleteHotel(Integer id);

}
