package com.thelodge.controller;

import com.thelodge.dto.JwtResponse;
import com.thelodge.dto.LoginRequest;
import com.thelodge.entity.auth.Users;
import com.thelodge.repository.UserRepository;
import com.thelodge.security.JwtUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtService;
    private final UserRepository userRepository;

    public AuthController(AuthenticationManager authenticationManager, 
                          JwtUtil jwtService,
                          UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        request.getUsername(), 
                        request.getPassword()
                    )
                );
            } catch (BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body("Invalid username or password");
            }

        Users user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}

