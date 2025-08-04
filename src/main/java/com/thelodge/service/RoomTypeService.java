package com.thelodge.service;

import com.thelodge.dto.RoomTypeDto;

import java.util.List;

public interface RoomTypeService {
    RoomTypeDto createRoomType(RoomTypeDto dto);
    List<RoomTypeDto> getAllRoomTypes();
    RoomTypeDto getRoomTypeById(Integer id);
    void deleteRoomType(Integer id);
}
