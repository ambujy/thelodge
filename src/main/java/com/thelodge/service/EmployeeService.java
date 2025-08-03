package com.thelodge.service;

import com.thelodge.dto.EmployeeRequestDto;
import com.thelodge.dto.EmployeeResponseDto;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDto createEmployee(EmployeeRequestDto dto);
    EmployeeResponseDto getEmployeeById(Integer id);
    List<EmployeeResponseDto> getAllEmployees();
    EmployeeResponseDto updateEmployee(Integer id, EmployeeRequestDto dto);
    void deleteEmployee(Integer id);
}
