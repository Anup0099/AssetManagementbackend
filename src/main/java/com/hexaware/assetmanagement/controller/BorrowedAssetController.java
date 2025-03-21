package com.hexaware.assetmanagement.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.assetmanagement.dto.BorrowedAssetDTO;
import com.hexaware.assetmanagement.exception.UserNotFoundException;
import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.model.BorrowedAsset;
import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.service.AssetServiceImpl;
import com.hexaware.assetmanagement.service.IBorrowedAssetService;
import com.hexaware.assetmanagement.service.UserServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

@RestController
@RequestMapping("/api/borrowed-assets")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class BorrowedAssetController {

	private static final Logger logger = LoggerFactory.getLogger(BorrowedAssetController.class);

	@Autowired
	private IBorrowedAssetService borrowedAssetService;
	private final String SECRET_KEY = "A3fN2/7KX12S3pHlTlg5R3ks7+TpK8MwN1R3lwBhvVs=";
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private AssetServiceImpl assetServiceImpl;
//Endpoint to borrow an asset
//	@PostMapping
//	public ResponseEntity<BorrowedAsset> borrowAsset(@RequestBody BorrowedAsset borrowedAsset) {
//		logger.info("Received request to borrow an asset with details: {}", borrowedAsset);
//		try {
//			BorrowedAsset savedAsset = borrowedAssetService.borrowAsset(borrowedAsset);
//			logger.info("Asset borrowed successfully with details: {}", savedAsset);
//			return ResponseEntity.ok(savedAsset);
//		} catch (Exception e) {
//			logger.error("Error occurred while borrowing asset: {}", borrowedAsset, e);
//			return ResponseEntity.status(500).build();
//		}
//	}

	@PostMapping
	public ResponseEntity<BorrowedAsset> borrowAsset(@RequestBody BorrowedAssetDTO borrowedAssetDTO,
			@RequestHeader("Authorization") String token) {
		logger.info("Recieved request to borrow asn asset with details:{}", borrowedAssetDTO);
		try {
			String jwt = token.replace("Bearer ", ""); 
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
			String emailString = claims.getSubject();

			User user = userServiceImpl.findUserByEmail(emailString)
					.orElseThrow(() -> new UserNotFoundException("User not found for email: " + emailString));
			if (user == null) {
				logger.error("User not found for email: {}", emailString);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
//			user.setUserId(borrowedAssetDTO.getUserId());
			Asset asset = assetServiceImpl.findAssetById(borrowedAssetDTO.getAssetId());
			if (asset == null) {
				logger.info("Asset not found for ID:{}", borrowedAssetDTO.getAssetId());
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
//			asset.setAssetId(borrowedAssetDTO.getAssetId());
			BorrowedAsset borrowedAssets = new BorrowedAsset();
			borrowedAssets.setUser(user);
			borrowedAssets.setAsset(asset);
			borrowedAssets.setBorrowDate(borrowedAssetDTO.getBorrowDate());
			borrowedAssets.setReturnDate(borrowedAssetDTO.getReturnDate());
			borrowedAssets.setStatus(borrowedAssetDTO.getStatus());

//			call the service to save the BorroedAsset
			BorrowedAsset savedAsset = borrowedAssetService.borrowAsset(borrowedAssets);
			logger.info("Asset borrowed successfully with details:{}", savedAsset);
			return ResponseEntity.ok(savedAsset);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Asset borroewed successfully with details:{}", e);
			return ResponseEntity.status(500).build();
		}
	}

	// Endpoint to find borrowed assets by user ID
	@GetMapping("/{borrowId}")
	public ResponseEntity<BorrowedAsset> findBorrowedAssetByBorrowId(@PathVariable Long borrowId) {
	    logger.info("Received request to find borrowed asset for borrowId: {}", borrowId);
	    try {
	        // Fetch the borrowed asset using the borrowId
	        Optional<BorrowedAsset> borrowedAsset = borrowedAssetService.findById(borrowId);

	        // Check if the asset exists
	        if (borrowedAsset.isPresent()) {
	            logger.info("Borrowed asset found for borrowId: {}", borrowId);
	            return ResponseEntity.ok(borrowedAsset.get());
	        } else {
	            logger.warn("No borrowed asset found for borrowId: {}", borrowId);
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        logger.error("Error occurred while fetching borrowed asset for borrowId: {}", borrowId, e);
	        return ResponseEntity.status(500).build();
	    }
	}
	
	@GetMapping
	public ResponseEntity<List<BorrowedAssetDTO>> getAllBorrowedAssets() {
	    logger.info("Fetching all borrowed assets");

	    try {
	        List<BorrowedAsset> borrowedAssets = borrowedAssetService.getAllBorrowedAssets();

	        // Map BorrowedAsset to BorrowedAssetDTO
	        List<BorrowedAssetDTO> borrowedAssetDTOs = borrowedAssets.stream().map(asset -> {
	            BorrowedAssetDTO dto = new BorrowedAssetDTO();
	            dto.setBorrowId(asset.getBorrowId());
	            dto.setAssetName(asset.getAsset().getAssetName());
	            dto.setUserName(asset.getUser().getUserDetails().getName());
	            dto.setBorrowDate(asset.getBorrowDate());
	            return dto;
	        }).collect(Collectors.toList());

	        return ResponseEntity.ok(borrowedAssetDTOs);
	    } catch (Exception e) {
	        logger.error("Error occurred while fetching borrowed assets", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}




//	@GetMapping("/all")
//	public ResponseEntity<Optional<List<BorrowedAsset>>> getAllBorrowedAsset() {
//		Optional<List<BorrowedAsset>> borrowedAssets = borrowedAssetService.getAllBorrowedAsset();
//		if (borrowedAssets.isEmpty()) {
//			return ResponseEntity.noContent().build();
//		}
//		return ResponseEntity.ok(borrowedAssets);
//
//	}
//	@GetMapping
//    public ResponseEntity<List<BorrowedAsset>> getAllBorrowedAssets() {
//        try {
//            List<BorrowedAsset> borrowedAssets = borrowedAssetService.getAllBorrowedAssets();
//            return ResponseEntity.ok(borrowedAssets);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }

//	@GetMapping("/searchByEmail")
//	public ResponseEntity<?> getByEmail(@RequestParam String email) {
//	    logger.info("Received request to find borrowed assets for email: {}", email);
//	    try {
//	        // Log the token and decode it to extract the email
//	        String token = email; // Assuming `email` parameter is passed in token (frontend handles this part)
//	        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.replace("Bearer ", "")).getBody();
//	        String decodedEmail = claims.getSubject(); // Assuming the email is stored as the subject in the token
//
//	        // If the email is decoded correctly
//	        if (decodedEmail == null || decodedEmail.isEmpty()) {
//	            logger.error("Email not found in the token.");
//	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found in token.");
//	        }
//
//	        // Fetch user by email
//	        User user = userServiceImpl.findUserByEmail(decodedEmail)
//	                .orElseThrow(() -> new UserNotFoundException("User not found for email:" + decodedEmail));
//	        System.out.println(user);
//	        logger.info("user not found",user);
//	        List<BorrowedAsset> assets = borrowedAssetService.findBorrowedAssetsByUserId(user.getUserId());
//
//	        if (assets.isEmpty()) {
//	            logger.info("No assets borrowed by user with email: {}", decodedEmail);
//	            return ResponseEntity.ok("No assets borrowed");
//	        }
//	        return ResponseEntity.ok(assets);
//
//	    } catch (UserNotFoundException ex) {
//	        logger.error("User not found for email: {}", email, ex);
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for email: " + email);
//	    } catch (Exception e) {
//	        logger.error("Error occurred while fetching borrowed assets for email: {}", email, e);
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//	    }
//	}
	@GetMapping("/searchByEmail")
	public ResponseEntity<?> getByEmail(@RequestHeader("Authorization") String authorizationHeader) {
		logger.info("Received request to find borrowed assets for email in token.");
		try {
			// Extract the token from the Authorization header
			String token = authorizationHeader.replace("Bearer ", "");

			if (token.isEmpty()) {
				logger.error("JWT token is missing.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JWT token is missing.");
			}

			// Decode the token and extract the email
			Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			String decodedEmail = claims.getSubject(); // Email is stored as the subject

			if (decodedEmail == null || decodedEmail.isEmpty()) {
				logger.error("Email not found in the token.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found in token.");
			}

			// Fetch the user by decoded email
			User user = userServiceImpl.findUserByEmail(decodedEmail)
					.orElseThrow(() -> new UserNotFoundException("User not found for email: " + decodedEmail));

			logger.info("User found: {}", user);

			// Fetch borrowed assets for the user
			List<BorrowedAsset> borrowedAssets = borrowedAssetService.findBorrowedAssetsByUserId(user.getUserId());

			if (borrowedAssets.isEmpty()) {
				logger.info("No assets borrowed by user with email: {}", decodedEmail);
				return ResponseEntity.ok("No assets borrowed");
			}

			// Transform BorrowedAsset entities into BorrowedAssetDTOs
			List<BorrowedAssetDTO> borrowedAssetDTOs = borrowedAssets.stream().map(asset -> {
				BorrowedAssetDTO dto = new BorrowedAssetDTO();
				dto.setUserId(user.getUserId());
				dto.setAssetId(asset.getAsset().getAssetId());
				dto.setBorrowDate(asset.getBorrowDate());
				dto.setReturnDate(asset.getReturnDate());
				dto.setStatus(asset.getStatus());
				return dto;
			}).collect(Collectors.toList());

			return ResponseEntity.ok(borrowedAssetDTOs);

		} catch (UserNotFoundException ex) {
			logger.error("User not found for email: {}", authorizationHeader, ex);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for email: " + authorizationHeader);
		} catch (MalformedJwtException e) {
			logger.error("Malformed JWT token.", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed JWT token.");
		} catch (Exception e) {
			logger.error("Error occurred while fetching borrowed assets.", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
