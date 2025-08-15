package com.thelodge.util;

import java.util.stream.Collectors;

import com.thelodge.dto.*;
import com.thelodge.entity.*;

public class DtoMapper {

    public static BookingResponseDto mapToBookingResponseDto(Booking booking) {
        if (booking == null)
            return null;

        return BookingResponseDto.builder()
                .id(booking.getId())
                .guest(mapToGuestDto(booking.getGuest()))
                .employee(mapToEmployeeDto(booking.getEmployee()))
                .hotel(mapToHotelDto(booking.getHotel()))
                .travelMode(mapToTravelModeDto(booking.getTravelMode()))
                .bookingDate(booking.getBookingDate().toLocalDateTime())
                .checkIn(booking.getCheckIn().toLocalDateTime())
                .checkOut(booking.getCheckOut().toLocalDateTime())
                .totalRooms(booking.getTotalRooms())
                // Map each BookingRoom to RoomResponseDto
                .rooms(
                        booking.getBookingRooms().stream()
                                .map(br -> mapToRoomDto(br.getRoom()))
                                .collect(Collectors.toList()))
                // Map each BookingGuest to GuestDto
                .guests(
                        booking.getBookingGuests().stream()
                                .map(bg -> mapToGuestDto(bg.getGuest()))
                                .collect(Collectors.toList()))
                .totalAmount(booking.getTotalAmount())
                .createdAt(booking.getCreatedAt().toLocalDateTime())
                .updatedAt(booking.getUpdatedAt() != null ? booking.getUpdatedAt().toLocalDateTime() : null)
                .deletedAt(booking.getDeletedAt() != null ? booking.getDeletedAt().toLocalDateTime() : null)
                .build();
    }

    public static BookingStatusDto mapToBookingStatusDto(BookingStatus status) {
        return BookingStatusDto.builder()
                .id(status.getId())
                .bookingId(status.getBooking().getId())
                .status(status.getStatus())
                .changedAt(null != status.getChangedAt() ? status.getChangedAt().toLocalDateTime() : null)
                .build();
    }

    public static BookingRoomDto mapToBookingRoomDto(BookingRoom bookingRoom) {
        if (bookingRoom == null)
            return null;

        return BookingRoomDto.builder()
                .roomId(bookingRoom.getRoom().getId())
                .build();
    }

    public static TravelModeDto mapToTravelModeDto(TravelMode travelMode) {
        if (travelMode == null)
            return null;

        return TravelModeDto.builder()
                .id(travelMode.getId())
                .name(travelMode.getName())
                .build();
    }

    public static RoomResponseDto mapToRoomDto(Room room) {
        if (room == null)
            return null;

        return RoomResponseDto.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomType(mapToRoomTypeDto(room.getRoomType()))
                .hotel(mapToHotelDto(room.getHotel()))
                .build();
    }

    public static Room mapToRoomEntity(RoomRequestDto dto, RoomType roomType, Hotel hotel) {
        if (dto == null)
            return null;

        return Room.builder()
                .roomNumber(dto.getRoomNumber())
                .roomType(roomType)
                .hotel(hotel)
                .build();
    }

    public static RoomTypeDto mapToRoomTypeDto(RoomType type) {
        if (type == null)
            return null;

        return RoomTypeDto.builder()
                .id(type.getId())
                .name(type.getName())
                .description(type.getDescription())
                .cost(type.getCost())
                .smokeFriendly(type.getSmokeFriendly())
                .petFriendly(type.getPetFriendly())
                .build();
    }

    public static GuestDto mapToGuestDto(Guest guest) {
        if (guest == null)
            return null;

        return GuestDto.builder()
                .id(guest.getId())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .phone(guest.getPhone())
                .email(guest.getEmail())
                .build();
    }

    public static GuestResponseDto mapToGuestResponseDto(Guest guest) {
        return GuestResponseDto.builder()
                .id(guest.getId())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .phone(guest.getPhone())
                .email(guest.getEmail())
                .idProofType(guest.getIdProofType())
                .idProofNo(guest.getIdProofNo())
                .idProofFile(guest.getIdProofFile())
                .dob(guest.getDob())
                .address(mapToAddressDto(guest.getAddress()))
                .hotel(mapToHotelDto(guest.getHotel()))
                .build();
    }

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        if (employee == null)
            return null;

        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .build();
    }

    public static EmployeeResponseDto mapToEmployeeResponseDto(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .gender(employee.getGender().name())
                .phone(employee.getPhone())
                .email(employee.getEmail())
                .idProofType(employee.getIdProofType())
                .idProofNo(employee.getIdProofNo())
                .idProofFile(employee.getIdProofFile())
                .dob(employee.getDob())
                .address(mapToAddressDto(employee.getAddress()))
                .designation(mapToDesignationDto(employee.getDesignation()))
                .hotel(mapToHotelDto(employee.getHotel()))
                .createdAt(employee.getCreatedAt())
                .updatedAt(employee.getUpdatedAt())
                .deletedAt(employee.getDeletedAt())
                .build();
    }

    public static HotelResponseDto mapToHotelAddressDto(Hotel hotel) {
        Address address = hotel.getAddress();

        return HotelResponseDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .contact1(hotel.getContact1())
                .contact2(hotel.getContact2())
                .contact3(hotel.getContact3())
                .email(hotel.getEmail())
                .website(hotel.getWebsite())
                .description(hotel.getDescription())
                .roomCapacity(hotel.getRoomCapacity())
                .address(mapToAddressDto(address))
                .build();
    }

    public static AddressDto mapToAddressDto(Address address) {
        if (address == null)
            return null;

        return AddressDto.builder()
                .id(address.getId())
                .line1(address.getLine1())
                .line2(address.getLine2())
                .line3(address.getLine3())
                .state(address.getState())
                .city(address.getCity())
                .pincode(address.getPincode())
                .build();
    }

    public static DesignationDto mapToDesignationDto(Designation designation) {
        if (designation == null)
            return null;

        return DesignationDto.builder()
                .id(designation.getId())
                .title(designation.getTitle())
                .build();
    }

    public static HotelDto mapToHotelDto(Hotel hotel) {
        if (hotel == null)
            return null;

        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .build();
    }

    // You can add more mappings here (Hotel, Employee, Guest, etc.)
}
