package com.hexaware.assetmanagement.controller;

import com.hexaware.assetmanagement.dto.LoginRequest;
import com.hexaware.assetmanagement.dto.RegisterRequest;
import com.hexaware.assetmanagement.service.AuthService;
import com.hexaware.assetmanagement.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }
  
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        logger.info("Received registration request for email: {}", registerRequest.getEmail());
        try {
            authService.registerNewUser(registerRequest);
            logger.info("User registered successfully with email: {}", registerRequest.getEmail());
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            logger.error("Error occurred during registration for email: {}", registerRequest.getEmail(), e);
            return ResponseEntity.status(500).body("Error occurred during registration");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for email: {}", loginRequest.getEmail());
        try {
            String token = authService.login(loginRequest.getEmail(), loginRequest.getPassword(), jwtUtil);
            logger.info("Login successful for email: {}", loginRequest.getEmail());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            logger.error("Login failed for email: {}", loginRequest.getEmail(), e);
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }
}

