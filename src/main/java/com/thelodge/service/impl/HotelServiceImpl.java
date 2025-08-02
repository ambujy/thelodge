package com.thelodge.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.thelodge.dto.AddressDto;
import com.thelodge.dto.HotelRequestDto;
import com.thelodge.dto.HotelResponseDto;
import com.thelodge.entity.Address;
import com.thelodge.entity.Hotel;
import com.thelodge.repository.AddressRepository;
import com.thelodge.repository.HotelRepository;
import com.thelodge.service.HotelService;



@Service
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final AddressRepository addressRepository;

    public HotelServiceImpl(HotelRepository hotelRepository, AddressRepository addressRepository) {
        this.hotelRepository = hotelRepository;
        this.addressRepository = addressRepository;
    }

    private HotelResponseDto mapToDto(Hotel hotel) {
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
                .address(AddressDto.builder()
                        .line1(address.getLine1())
                        .line2(address.getLine2())
                        .line3(address.getLine3())
                        .city(address.getCity())
                        .state(address.getState())
                        .pincode(address.getPincode())
                        .build())
                .build();
    }

    @Override
    public HotelResponseDto createHotel(HotelRequestDto hotelRequestDto) {
        // Build and persist address
        AddressDto addressDto = hotelRequestDto.getAddress();
        Address address = Address.builder()
                .line1(addressDto.getLine1())
                .line2(addressDto.getLine2())
                .line3(addressDto.getLine3())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .pincode(addressDto.getPincode())
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();
        address = addressRepository.save(address);

        // Build and persist hotel
        Hotel hotel = Hotel.builder()
                .name(hotelRequestDto.getName())
                .contact1(hotelRequestDto.getContact1())
                .contact2(hotelRequestDto.getContact2())
                .contact3(hotelRequestDto.getContact3())
                .email(hotelRequestDto.getEmail())
                .website(hotelRequestDto.getWebsite())
                .description(hotelRequestDto.getDescription())
                .roomCapacity(hotelRequestDto.getRoomCapacity())
                .address(address) // Assign the persisted Address entity
                .createdAt(Timestamp.from(Instant.now()))
                .updatedAt(Timestamp.from(Instant.now()))
                .build();

        hotel = hotelRepository.save(hotel);

        // Build response DTO (customize as needed)
        return mapToDto(hotel);
    }


    @Override
    public List<HotelResponseDto> getAllHotels() {
        // Implementation for fetching all hotels
        return hotelRepository.findAll().stream()
                .map(this::mapToDto)
                .toList(); // Convert to List<HotelResponseDto>
    }

    @Override
    public HotelResponseDto getHotelById(Integer id) {
        // Implementation for fetching a hotel by ID
        return hotelRepository.findById(id)
                .map(this::mapToDto)
                .orElse(null); // Return null if hotel not found
    }

    @Override
    public HotelResponseDto updateHotel(Integer id, HotelRequestDto hotelRequestDto) {
        Hotel hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + id));

        // Update hotel fields
        hotel.setName(hotelRequestDto.getName());
        hotel.setContact1(hotelRequestDto.getContact1());
        hotel.setContact2(hotelRequestDto.getContact2());
        hotel.setContact3(hotelRequestDto.getContact3());
        hotel.setEmail(hotelRequestDto.getEmail());
        hotel.setWebsite(hotelRequestDto.getWebsite());
        hotel.setDescription(hotelRequestDto.getDescription());
        hotel.setRoomCapacity(hotelRequestDto.getRoomCapacity());

        // Update address (assuming address is not null)
        Address address = hotel.getAddress();
        address.setLine1(hotelRequestDto.getAddress().getLine1());
        address.setLine2(hotelRequestDto.getAddress().getLine2());
        address.setLine3(hotelRequestDto.getAddress().getLine3());
        address.setCity(hotelRequestDto.getAddress().getCity());
        address.setState(hotelRequestDto.getAddress().getState());
        address.setPincode(hotelRequestDto.getAddress().getPincode());

        // Save both entities
        addressRepository.save(address);
        Hotel updatedHotel = hotelRepository.save(hotel);

        return mapToDto(updatedHotel);
    }


    @Override
    public void deleteHotel(Integer id) {
        hotelRepository.deleteById(id);
    }


}
