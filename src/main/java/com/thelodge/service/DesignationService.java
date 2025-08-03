package com.thelodge.service;

import com.thelodge.dto.DesignationDto;

import java.util.List;

public interface DesignationService {
    DesignationDto createDesignation(DesignationDto dto);
    List<DesignationDto> getAllDesignations();
    DesignationDto getDesignationById(Integer id);
}
