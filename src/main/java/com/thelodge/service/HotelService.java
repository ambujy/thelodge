package com.thelodge.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.thelodge.dto.HotelRequestDto;
import com.thelodge.dto.HotelResponseDto;
import com.thelodge.dto.PageDTO;

public interface HotelService {

    HotelResponseDto createHotel(HotelRequestDto hotelRequestDto);
    // List<HotelResponseDto> getAllHotels();
    HotelResponseDto getHotelById(Integer id);
    HotelResponseDto updateHotel(Integer id, HotelRequestDto hotelRequestDto);
    void deleteHotel(Integer id);
    PageDTO<HotelResponseDto> getAllHotels(Pageable pageable);

}
