package com.thelodge.service.impl;

import com.thelodge.dto.BookingRequestDto;
import com.thelodge.dto.BookingResponseDto;
import com.thelodge.dto.EmployeeDto;
import com.thelodge.dto.HotelDto;
import com.thelodge.dto.RoomRequestDto;
import com.thelodge.dto.TravelModeDto;
import com.thelodge.entity.Booking;
import com.thelodge.entity.BookingGuest;
import com.thelodge.entity.BookingRoom;
import com.thelodge.entity.BookingStatus;
import com.thelodge.entity.Employee;
import com.thelodge.entity.Guest;
import com.thelodge.entity.Hotel;
import com.thelodge.entity.Room;
import com.thelodge.entity.TravelMode;
import com.thelodge.enums.BookingStatusType;
import com.thelodge.repository.BookingGuestRepository;
import com.thelodge.repository.BookingRepository;
import com.thelodge.repository.BookingRoomRepository;
import com.thelodge.repository.BookingStatusRepository;
import com.thelodge.repository.EmployeeRepository;
import com.thelodge.repository.GuestRepository;
import com.thelodge.repository.HotelRepository;
import com.thelodge.repository.RoomRepository;
import com.thelodge.repository.TravelModeRepository;
import com.thelodge.service.BookingService;
import com.thelodge.util.DtoMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final TravelModeRepository travelModeRepository;
    private final RoomRepository roomRepository;
    private final BookingRoomRepository bookingRoomRepository;
    private final BookingGuestRepository bookingGuestRepository;
    private final BookingStatusRepository bookingStatusRepository;
    private final EmployeeRepository employeeRepository;
    private final HotelRepository hotelRepository;

    @Override
    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto requestDTO) {
        // Create or retrieve guests
        if (requestDTO.getGuestId() == null) {
            throw new RuntimeException("Guest is required");
        }
        Guest guest = guestRepository.findById(requestDTO.getGuestId())
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        // Fetch travel mode

        if (requestDTO.getTravelModeId() == null) {
            throw new RuntimeException("Travel Mode is required");
        }

        TravelMode travelMode = travelModeRepository.findById(requestDTO.getTravelModeId())
                .orElseThrow(() -> new RuntimeException("Travel Mode not found"));

        // Employee validation
        if (requestDTO.getEmployeeId() == null) {
            throw new RuntimeException("Employee is required");
        }

        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Hotel validation
        if (requestDTO.getHotelId() == null) {
            throw new RuntimeException("Hotel is required");
        }
        Hotel hotel = hotelRepository.findById(requestDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Create booking entity

        LocalDateTime checkIn = requestDTO.getCheckIn();
        Timestamp checkInTimestamp = (checkIn != null) ? Timestamp.valueOf(checkIn) : null;

        LocalDateTime checkOut = requestDTO.getCheckOut();
        Timestamp checkOutTimestamp = (checkOut != null) ? Timestamp.valueOf(checkOut) : null;

        int totalRooms = requestDTO.getTotalRooms();

        Booking booking = Booking.builder()
                .guest(guest)
                .travelMode(travelMode)
                .employee(employee)
                .hotel(hotel)
                .totalRooms(totalRooms)
                .totalAmount(requestDTO.getTotalAmount())
                .checkIn(checkInTimestamp)
                .checkOut(checkOutTimestamp)
                .bookingDate(Timestamp.valueOf(LocalDateTime.now()))
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        // Allocate guests for this booking
        for (Integer guestId : requestDTO.getGuests()) {
            if (guestId == null) {
                throw new RuntimeException("Guest ID cannot be null");
            }

            Guest guests = guestRepository.findById(guestId)
                    .orElseThrow(() -> new RuntimeException("Guest not found: " + guestId));

            BookingGuest bookingGuest = BookingGuest.builder()
                    .booking(booking)
                    .guest(guests)
                    .build();
            bookingGuestRepository.save(bookingGuest);
        }

        // Allocate rooms for this booking
        for (Integer roomId : requestDTO.getRooms()) {
            if (roomId == null) {
                throw new RuntimeException("Room number cannot be null");
            }

            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

            BookingRoom bookingRoom = BookingRoom.builder()
                    .booking(booking)
                    .room(room)
                    .build();
            bookingRoomRepository.save(bookingRoom);
        }

        try {
            booking = bookingRepository.save(booking);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Guest ID: " + guest.getId());
            System.out.println("Employee ID: " + employee.getId());
            System.out.println("Hotel ID: " + hotel.getId());
            System.out.println("Travel Mode ID: " + travelMode.getId());
            throw e;
        }

        // Add booking status (initial: BOOKED)
        BookingStatus status = BookingStatus.builder()
                .booking(booking)
                .status(BookingStatusType.BOOKED)
                .changedAt(Timestamp.valueOf(LocalDateTime.now()))
                .changedBy(employee.getFirstName() + " " + employee.getLastName()) 
                .build();

        bookingStatusRepository.save(status);

        return DtoMapper.mapToBookingResponseDto(booking);
    }

    @Override
    public BookingResponseDto getBookingById(Integer id) {
        // Fetch booking by ID
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        // Map to DTO
        BookingResponseDto dto = DtoMapper.mapToBookingResponseDto(booking);

        // Fetch single status
        BookingStatus status = bookingStatusRepository.findByBookingId(booking.getId());
        if (status != null) {
            dto.setStatus(status.getStatus().name());
        }

        return dto;
    }

    @Override
    public List<BookingResponseDto> getAllBookings() {
        // Fetch all bookings and map to DTOs
        return bookingRepository.findAll().stream()
                .map(booking -> {

                    BookingResponseDto dto = DtoMapper.mapToBookingResponseDto(booking);

                    // Fetch single status
                    BookingStatus status = bookingStatusRepository.findByBookingId(booking.getId());
                    if (status != null) {
                        dto.setStatus(status.getStatus().name());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto updateBooking(Integer id, BookingRequestDto requestDTO) {
        // Get existing booking
        Booking existingBooking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        // Fetch travel mode

        if (requestDTO.getTravelModeId() == null) {
            throw new RuntimeException("Travel Mode is required");
        }

        TravelMode travelMode = travelModeRepository.findById(requestDTO.getTravelModeId())
                .orElseThrow(() -> new RuntimeException("Travel Mode not found"));

        // Employee validation
        if (requestDTO.getEmployeeId() == null) {
            throw new RuntimeException("Employee is required");
        }

        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Hotel validation
        if (requestDTO.getHotelId() == null) {
            throw new RuntimeException("Hotel is required");
        }
        Hotel hotel = hotelRepository.findById(requestDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        // Create booking entity

        LocalDateTime checkIn = requestDTO.getCheckIn();
        Timestamp checkInTimestamp = (checkIn != null) ? Timestamp.valueOf(checkIn) : null;

        LocalDateTime checkOut = requestDTO.getCheckOut();
        Timestamp checkOutTimestamp = (checkOut != null) ? Timestamp.valueOf(checkOut) : null;

        LocalDateTime bookingDateTime = requestDTO.getBookingDate();
        Timestamp bookingDateTimestamp = (bookingDateTime != null) ? Timestamp.valueOf(bookingDateTime) : null;

        int totalRooms = requestDTO.getTotalRooms();

        Booking booking = Booking.builder()
                .guest(existingBooking.getGuest())
                .bookingDate(bookingDateTimestamp)
                .travelMode(travelMode)
                .employee(employee)
                .hotel(hotel)
                .totalRooms(totalRooms)
                .totalAmount(requestDTO.getTotalAmount())
                .checkIn(checkInTimestamp)
                .checkOut(checkOutTimestamp)
                .updatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        // Allocate rooms for this booking
        for (Integer roomId : requestDTO.getRooms()) {
            if (roomId == null) {
                throw new RuntimeException("Room number cannot be null");
            }

            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

            BookingRoom bookingRoom = BookingRoom.builder()
                    .booking(booking)
                    .room(room)
                    .build();
            bookingRoomRepository.save(bookingRoom);
        }


        booking = bookingRepository.save(booking);

        // Add booking status (initial: BOOKED)
        BookingStatus status = BookingStatus.builder()
                .booking(booking)
                .status(requestDTO.getStatus() != null ? requestDTO.getStatus() : BookingStatusType.BOOKED)
                .changedAt(Timestamp.valueOf(LocalDateTime.now()))
                .changedBy(employee.getFirstName() + " " + employee.getLastName()) 
                .build();

        bookingStatusRepository.save(status);

        return DtoMapper.mapToBookingResponseDto(booking);
    }

    @Override
    public List<Room> getRoomsByBookingId(Integer bookingId) {
        List<BookingRoom> bookingRooms = bookingRoomRepository.findByBookingId(bookingId);
        return bookingRooms.stream()
                .map(BookingRoom::getRoom)
                .collect(Collectors.toList());
    }
}