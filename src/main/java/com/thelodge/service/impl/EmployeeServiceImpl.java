package com.thelodge.service.impl;

import com.thelodge.dto.AddressDto;
import com.thelodge.dto.DesignationDto;
import com.thelodge.dto.EmployeeRequestDto;
import com.thelodge.dto.EmployeeResponseDto;
import com.thelodge.dto.HotelDto;
import com.thelodge.entity.Address;
import com.thelodge.entity.Designation;
import com.thelodge.entity.Employee;
import com.thelodge.entity.Hotel;
import com.thelodge.enums.GenderType;
import com.thelodge.repository.AddressRepository;
import com.thelodge.repository.DesignationRepository;
import com.thelodge.repository.EmployeeRepository;
import com.thelodge.repository.HotelRepository;
import com.thelodge.service.EmployeeService;
import com.thelodge.util.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

        private final EmployeeRepository employeeRepository;
        private final HotelRepository hotelRepository;
        private final DesignationRepository designationRepository;
        private final AddressRepository addressRepository;

        @Override
        public EmployeeResponseDto createEmployee(EmployeeRequestDto dto) {

                HotelDto hotelDto = dto.getHotel();

                Hotel hotel = hotelRepository.findById(hotelDto.getId())
                                .orElseThrow(() -> new RuntimeException("Hotel not found"));

                DesignationDto designationDto = dto.getDesignation();

                Designation designation = designationRepository.findById(designationDto.getId())
                                .orElseThrow(() -> new RuntimeException("Designation not found"));

                AddressDto addressDto = dto.getAddress();

                Address address = Address.builder()
                                .line1(addressDto.getLine1())
                                .line2(addressDto.getLine2())
                                .line3(addressDto.getLine3())
                                .city(addressDto.getCity())
                                .state(addressDto.getState())
                                .pincode(addressDto.getPincode())
                                .build();

                address = addressRepository.save(address);

                Employee employee = Employee.builder()
                                .firstName(dto.getFirstName())
                                .lastName(dto.getLastName())
                                .gender(parseGender(dto.getGender()))
                                .phone(dto.getPhone())
                                .email(dto.getEmail())
                                .hotel(hotel)
                                .designation(designation)
                                .address(address)
                                .build();

                return DtoMapper.mapToEmployeeResponseDto(employeeRepository.save(employee));
        }

        @Override
        public EmployeeResponseDto getEmployeeById(Integer id) {
                Employee employee = employeeRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Employee not found"));
                return DtoMapper.mapToEmployeeResponseDto(employee);
        }

        @Override
        public List<EmployeeResponseDto> getAllEmployees() {
                return employeeRepository.findAll()
                                .stream()
                                .map(DtoMapper::mapToEmployeeResponseDto)
                                .toList();
        }

        @Override
        public EmployeeResponseDto updateEmployee(Integer id, EmployeeRequestDto dto) {
                Employee employee = employeeRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Employee not found"));

                HotelDto hotelDto = dto.getHotel();

                Hotel hotel = hotelRepository.findById(hotelDto.getId())
                                .orElseThrow(() -> new RuntimeException("Hotel not found"));

                AddressDto addressDto = dto.getAddress();

                Address address = addressRepository.findById(addressDto.getId())
                                .orElseThrow(() -> new RuntimeException("Address not found"));

                address.setLine1(addressDto.getLine1());
                address.setLine2(addressDto.getLine2());
                address.setLine3(addressDto.getLine3());
                address.setCity(addressDto.getCity());
                address.setState(addressDto.getState());
                address.setPincode(addressDto.getPincode());
                address = addressRepository.save(address);

                DesignationDto designationDto = dto.getDesignation();

                Designation designation = designationRepository.findById(designationDto.getId())
                                .orElseThrow(() -> new RuntimeException("Designation not found"));

                employee.setFirstName(dto.getFirstName());
                employee.setLastName(dto.getLastName());
                employee.setGender(parseGender(dto.getGender()));
                employee.setPhone(dto.getPhone());
                employee.setEmail(dto.getEmail());
                employee.setAddress(address);
                employee.setHotel(hotel);
                employee.setDesignation(designation);

                return DtoMapper.mapToEmployeeResponseDto(employeeRepository.save(employee));
        }

        @Override
        public void deleteEmployee(Integer id) {
                employeeRepository.deleteById(id);
        }

        private GenderType parseGender(String genderStr) {
                if (genderStr == null) {
                        throw new IllegalArgumentException("Gender cannot be null");
                }

                for (GenderType gender : GenderType.values()) {
                        if (gender.name().equalsIgnoreCase(genderStr.trim())) {
                                return gender;
                        }
                }

                throw new IllegalArgumentException("Invalid gender value: " + genderStr +
                                ". Allowed values: " + List.of(GenderType.values()));
        }

}
