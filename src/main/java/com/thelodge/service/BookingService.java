package com.thelodge.service;

import com.thelodge.dto.BookingRequestDto;
import com.thelodge.dto.BookingResponseDto;
import com.thelodge.entity.Room;

import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingRequestDto requestDTO);
    BookingResponseDto getBookingById(Integer id);
    List<BookingResponseDto> getAllBookings();
    BookingResponseDto updateBooking(Integer id, BookingRequestDto requestDTO);
    List<Room> getRoomsByBookingId(Integer bookingId);
}
