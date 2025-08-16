package com.thelodge.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.thelodge.entity.auth.Users;

import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtUtil {

    // private final SecretKey SECRET_KEY;
    // private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    // public JwtUtil() {
    //     // Generates a secure random key for HS256 (minimum 256 bits)
    //     // this.SECRET_KEY = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    //     this.SECRET_KEY = Jwts.SIG.HS256.key().build();

    //     // Print the Base64 version so you can use it for verification in Postman/jwt.io if needed
    //     System.out.println("Generated JWT Secret Key: " +
    //             Base64.getEncoder().encodeToString(SECRET_KEY.getEncoded()));
    // }

    // private SecretKey getSigningKey() {
    //     return SECRET_KEY;
    // }

    

    private static final String SECRET_KEY_BASE64 = "c0414bccaa066b91fc85602692b596dca591a4652e431c830ca32447e1d5e8d6"; 
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private SecretKey getSigningKey() {
        // The key above is BASE64-encoded, so decode it first
        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY_BASE64);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .and()
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
