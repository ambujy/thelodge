package com.thelodge.controller;

import com.thelodge.dto.DesignationDto;
import com.thelodge.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
@RequiredArgsConstructor
public class DesignationController {

    private final DesignationService designationService;

    @PostMapping
    public ResponseEntity<DesignationDto> createDesignation(@RequestBody DesignationDto dto) {
        return ResponseEntity.ok(designationService.createDesignation(dto));
    }

    @GetMapping
    public ResponseEntity<List<DesignationDto>> getAllDesignations() {
        return ResponseEntity.ok(designationService.getAllDesignations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DesignationDto> getDesignationById(@PathVariable Integer id) {
        return ResponseEntity.ok(designationService.getDesignationById(id));
    }
}
