package com.hexaware.assetmanagement.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dto.RegisterRequest;
import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.model.UserDetails;
import com.hexaware.assetmanagement.repository.UserRepository;
import com.hexaware.assetmanagement.role.Role;
import com.hexaware.assetmanagement.util.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user with the given details.
     *
     * @param registerRequest the user registration details.
     * @return the registered user.
     */
    public User registerNewUser(RegisterRequest registerRequest) {
        // Check if the email is already in use
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already in use!");
        }

        // Encode the password for security
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Create a new User entity
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(Role.valueOf(registerRequest.getRole().toUpperCase())); // Convert role to enum

        // Create associated UserDetails
        UserDetails userDetails = new UserDetails();
        userDetails.setName(registerRequest.getName());
        userDetails.setPhoneNumber(registerRequest.getPhoneNumber());
        userDetails.setAddress(registerRequest.getAddress());
        userDetails.setUser(user); // Link UserDetails to User

        // Link User to UserDetails
        user.setUserDetails(userDetails);

        // Save the User entity (CascadeType.ALL ensures UserDetails is saved too)
        return userRepository.save(user);
    }

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param email the user's email.
     * @param password the user's password.
     * @param jwtUtil the JWT utility for token generation.
     * @return the generated JWT token.
     */
    public String login(String email, String password, JwtUtil jwtUtil) {
        // Fetch the user from the database by email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials!"));

        // Check if the provided password matches the stored encoded password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        // Generate and return a JWT token
        return jwtUtil.generateToken(user.getEmail(), user.getRole().name());
    }
}
