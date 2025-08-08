package com.thelodge.controller;

import com.thelodge.dto.BookingRequestDto;
import com.thelodge.dto.BookingResponseDto;
import com.thelodge.service.BookingService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponseDto> createBooking(@RequestBody BookingRequestDto bookingRequestDTO) {
        BookingResponseDto responseDTO = bookingService.createBooking(bookingRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable Integer bookingId) {
        BookingResponseDto responseDTO = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        List<BookingResponseDto> responseDTOs = bookingService.getAllBookings();
        return ResponseEntity.ok(responseDTOs);
    }

    // You can add more endpoints as needed, for example:
    // - updateBooking
    // - cancelBooking
    // - listAllBookings
    // - getBookingsByGuest, etc.
}
