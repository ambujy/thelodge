package com.thelodge.service.impl;

import com.thelodge.dto.DesignationDto;
import com.thelodge.entity.Designation;
import com.thelodge.repository.DesignationRepository;
import com.thelodge.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesignationServiceImpl implements DesignationService {

    private final DesignationRepository designationRepository;

    @Override
    public DesignationDto createDesignation(DesignationDto dto) {
        Designation designation = Designation.builder()
                .title(dto.getTitle())
                .build();
        Designation saved = designationRepository.save(designation);
        return mapToDto(saved);
    }

    @Override
    public List<DesignationDto> getAllDesignations() {
        return designationRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DesignationDto getDesignationById(Integer id) {
        Designation designation = designationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Designation not found"));
        return mapToDto(designation);
    }

    private DesignationDto mapToDto(Designation designation) {
        return DesignationDto.builder()
                .id(designation.getId())
                .title(designation.getTitle())
                .build();
    }
}
