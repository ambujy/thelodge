package com.thelodge.service;

import com.thelodge.dto.RoomRequestDto;
import com.thelodge.dto.RoomResponseDto;

import java.util.List;

public interface RoomService {
    RoomResponseDto createRoom(RoomRequestDto requestDto);
    List<RoomResponseDto> getAllRooms();
    RoomResponseDto getRoomById(Integer id);
    void deleteRoom(Integer id);
}
