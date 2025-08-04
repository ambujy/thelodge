package com.thelodge.controller;

import com.thelodge.dto.RoomTypeDto;
import com.thelodge.service.RoomTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room-types")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;

    @PostMapping
    public ResponseEntity<RoomTypeDto> createRoomType(@RequestBody RoomTypeDto dto) {
        RoomTypeDto created = roomTypeService.createRoomType(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<RoomTypeDto>> getAllRoomTypes() {
        return ResponseEntity.ok(roomTypeService.getAllRoomTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomTypeDto> getRoomTypeById(@PathVariable Integer id) {
        return ResponseEntity.ok(roomTypeService.getRoomTypeById(id));
    }
}
