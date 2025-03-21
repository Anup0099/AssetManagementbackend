package com.hexaware.assetmanagement.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.role.Role;
import com.hexaware.assetmanagement.service.IUserService;
import com.hexaware.assetmanagement.util.JwtResponse;
import com.hexaware.assetmanagement.util.JwtUtil;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000") 
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private IUserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// Register a new user
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		logger.info("Received request to register a new user: {}", user);

		try {
			// Check if the role is valid
			if (user.getRole() == null
					|| (!user.getRole().equalsIgnoreCase("USER") && !user.getRole().equalsIgnoreCase("ADMIN"))) {
				logger.error("Invalid role provided: {}", user.getRole());
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Return 400 for invalid role
			}

			// Map the role to enum (assuming your Role enum has USER and ADMIN)
			try {
				user.setRole(Role.valueOf(user.getRole().toUpperCase())); // Convert string to enum
			} catch (IllegalArgumentException e) {
				logger.error("Invalid role provided, failed to map to Role enum: {}", user.getRole());
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Return 400 if role mapping fails
			}

			// Ensure UserDetails is set properly
			if (user.getUserDetails() != null) {
				user.getUserDetails().setUser(user); // Link back to the User
				logger.debug("Linked user details to the user object: {}", user.getUserDetails());
			}

			// Register the user
			User savedUser = userService.registerUser(user);
			logger.info("User registered successfully: {}", savedUser);
			return new ResponseEntity<>(savedUser, HttpStatus.CREATED); // Return status 201 for successful creation

		} catch (Exception e) {
			logger.error("Error registering user: {}", e.getMessage(), e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Return 500 in case of any error
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User loginCredentials) {
		logger.info("Received login request for email: {}", loginCredentials.getEmail());
		try {
			Optional<User> userOptional = userService.findUserByEmail(loginCredentials.getEmail());

			if (userOptional.isPresent()) {
				User user = userOptional.get();
				// Verify password (ensure the password is hashed in the database)
				if (user.getPassword().equals(loginCredentials.getPassword())) {
					logger.info("Login successful for user: {}", user.getEmail());

					// Generate JWT token using the email and role
					String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

					// Return the token in response
					System.out.println(token);
					return ResponseEntity.ok(new JwtResponse(token)); // Return token in the response DTO
				} else {
					logger.warn("Invalid credentials for email: {}", loginCredentials.getEmail());
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // Incorrect password
				}
			} else {
				logger.warn("User not found for email: {}", loginCredentials.getEmail());
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); // User not found
			}
		} catch (Exception e) {
			logger.error("Error during login process: {}", e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Internal error
		}
	}

	// Get user by ID
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
		logger.info("Received request to fetch user by ID: {}", userId);
		Optional<User> user = userService.findUserById(userId);
		if (user.isPresent()) {
			logger.info("User found with ID {}: {}", userId, user.get());
			return new ResponseEntity<>(user.get(), HttpStatus.OK); // Return status 200 with user details
		} else {
			logger.warn("User not found with ID: {}", userId);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return status 404 if user is not found
		}
	}

	// Get user by email
	@GetMapping("/email/{email}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email) {
		logger.info("Received request to fetch user by email: {}", email);
		Optional<User> user = userService.findUserByEmail(email);
		if (user.isPresent()) {
			logger.info("User found with email {}: {}", email, user.get());
			return new ResponseEntity<>(user.get(), HttpStatus.OK); // Return status 200 with user details
		} else {
			logger.warn("User not found with email: {}", email);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return status 404 if user is not found
		}
	}

	// Get all users
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAll() {
		logger.info("Received request to fetch all users");
		List<User> users = userService.getAllUsers();
		logger.info("Fetched all users: {}", users);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
