package com.thelodge.service.impl;

import com.thelodge.dto.RoomTypeDto;
import com.thelodge.entity.RoomType;
import com.thelodge.repository.RoomTypeRepository;
import com.thelodge.service.RoomTypeService;
import com.thelodge.util.DtoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomTypeServiceImpl implements RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;

    @Override
    public RoomTypeDto createRoomType(RoomTypeDto dto) {
        RoomType roomType = RoomType.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .cost(dto.getCost())
                .smokeFriendly(dto.getSmokeFriendly())
                .petFriendly(dto.getPetFriendly())
                .build();

        RoomType saved = roomTypeRepository.save(roomType);
        return DtoMapper.mapToRoomTypeDto(saved);
    }

    @Override
    public List<RoomTypeDto> getAllRoomTypes() {
        return roomTypeRepository.findAll().stream()
                .map(DtoMapper::mapToRoomTypeDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomTypeDto getRoomTypeById(Integer id) {
        return roomTypeRepository.findById(id)
                .map(DtoMapper::mapToRoomTypeDto)
                .orElseThrow(() -> new EntityNotFoundException("Room type not found"));
    }

    public void deleteRoomType(Integer id) {
        if (!roomTypeRepository.existsById(id)) {
            throw new EntityNotFoundException("Room type not found");
        }
        roomTypeRepository.deleteById(id);
    }
}
