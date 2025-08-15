package com.thelodge.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.thelodge.entity.auth.Users;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    @GetMapping("/")
    public String home() {
        return "Welcome to the home page!";
    }

    @PostMapping()
    public Users createUsers(@RequestBody Users entity) {
        
        return entity;
    }
    

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // Only users with USER or ADMIN role
    public String userDashboard() {
        return "Welcome, User! You have access to user features.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')") // Only users with ADMIN role
    public String adminDashboard() {
        return "Welcome, Admin! You have access to administrative features.";
    }

    @GetMapping("/data/sensitive")
    @PreAuthorize("hasAuthority('READ_SENSITIVE_DATA')") // Only users with READ_SENSITIVE_DATA permission
    public String sensitiveData() {
        return "This is highly sensitive data that only certain roles can see.";
    }

    @GetMapping("/public")
    public String publicAccess() {
        return "This content is accessible to everyone, authenticated or not.";
    }
}