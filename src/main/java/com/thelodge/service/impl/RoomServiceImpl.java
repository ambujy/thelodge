package com.thelodge.service.impl;

import com.thelodge.dto.HotelDto;
import com.thelodge.dto.RoomRequestDto;
import com.thelodge.dto.RoomResponseDto;
import com.thelodge.entity.Hotel;
import com.thelodge.entity.Room;
import com.thelodge.entity.RoomType;
import com.thelodge.repository.HotelRepository;
import com.thelodge.repository.RoomRepository;
import com.thelodge.repository.RoomTypeRepository;
import com.thelodge.service.RoomService;
import com.thelodge.util.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;

    @Override
    public RoomResponseDto createRoom(RoomRequestDto dto) {
        
        RoomType roomType = roomTypeRepository.findById(dto.getRoomTypeId())
                .orElseThrow(() -> new EntityNotFoundException("RoomType not found"));

        HotelDto hotelDto = dto.getHotel();
        if (hotelDto == null || hotelDto.getId() == null) {
            throw new IllegalArgumentException("Hotel information is required");
        }

        Hotel hotel = hotelRepository.findById(hotelDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Hotel not found"));

        Room room = DtoMapper.mapToRoomEntity(dto, roomType, hotel);
        Room saved = roomRepository.save(room);
        return DtoMapper.mapToRoomDto(saved);
    }

    @Override
    public List<RoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(DtoMapper::mapToRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponseDto getRoomById(Integer id) {
        return roomRepository.findById(id)
                .map(DtoMapper::mapToRoomDto)
                .orElseThrow(() -> new EntityNotFoundException("Room not found"));
    }

    @Override
    public void deleteRoom(Integer id) {
        if (!roomRepository.existsById(id)) {
            throw new EntityNotFoundException("Room not found");
        }
        roomRepository.deleteById(id);
    }
}
