package com.thelodge.controller;

import com.thelodge.dto.GuestRequestDto;
import com.thelodge.dto.GuestResponseDto;
import com.thelodge.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<GuestResponseDto> createGuest(@RequestBody GuestRequestDto guestRequestDto) {
        return ResponseEntity.ok(guestService.createGuest(guestRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestResponseDto> getGuestById(@PathVariable Integer id) {
        return ResponseEntity.ok(guestService.getGuestById(id));
    }

    @GetMapping
    public ResponseEntity<List<GuestResponseDto>> getAllGuests() {
        return ResponseEntity.ok(guestService.getAllGuests());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestResponseDto> updateGuest(@PathVariable Integer id,
                                                        @RequestBody GuestRequestDto guestRequestDto) {
        return ResponseEntity.ok(guestService.updateGuest(id, guestRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Integer id) {
        guestService.deleteGuest(id);
        return ResponseEntity.noContent().build();
    }
}
