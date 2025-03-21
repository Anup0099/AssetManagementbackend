package com.hexaware.assetmanagement.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.exception.AssetNotFoundException;
import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.repository.BorrowedAssetRepository;
import com.hexaware.assetmanagement.service.IAssetService;
import com.hexaware.assetmanagement.util.JwtUtil;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "http://localhost:3000") // Allow requests from React
public class AssetController {

	private static final Logger logger = LogManager.getLogger(AssetController.class);

	@Autowired
	private IAssetService assetService;
	@Autowired
	private AssetControllerHelper assetControllerHelper; // Inject the helper
	@Autowired
	private BorrowedAssetRepository borrowedAssetRepository;
	@Autowired
	private JwtUtil jwtUtil;
	private final String uploadDirectory = "C:/uploads/assets/"; // Path to store the uploaded images

	// Add a new asset
//	@PostMapping
//	public ResponseEntity<Asset> addAsset(@RequestBody Asset asset,
//			@RequestParam(value = "image", required = false) MultipartFile image) {
//		logger.info("Received request to add a new asset: {}", asset);
//		try {
//			if (image != null && !image.isEmpty()) {
//				// generate a unique file name
//				String fileNameString = UUID.randomUUID() + "_" + image.getOriginalFilename();
//				// get the download folder dynamically
//				String userHome = System.getProperty("user.home");
//				Path downloadsPaths = Paths.get(userHome, "Downloads", fileNameString);
//				Files.createDirectories(downloadsPaths.getParent());
//				Files.write(downloadsPaths, image.getBytes());
//				asset.setImageURlString("/downloads/" + fileNameString);
//
//			}
//			Asset savedAsset = assetService.addAsset(asset);
//			logger.info("Successfully assed asset with ID:{}", savedAsset.getAssetId());
//			return new ResponseEntity<Asset>(savedAsset, HttpStatus.CREATED);
//		} catch (Exception e) {
//			logger.error("Failed to add asset: {}", asset, e);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//		}
//	}
	
	@PostMapping
	public ResponseEntity<Asset> addAsset(@RequestBody Asset asset) {
	    logger.info("Received request to add a new asset: {}", asset);
	    try {
	        Asset savedAsset = assetService.addAsset(asset);
	        logger.info("Successfully added asset with ID: {}", savedAsset.getAssetId());
	        return new ResponseEntity<>(savedAsset, HttpStatus.CREATED);
	    } catch (Exception e) {
	        logger.error("Failed to add asset: {}", asset, e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	// Get all assets
	@GetMapping
	public ResponseEntity<List<Asset>> getAllAssets(@RequestHeader("Authorization") String authHeader) {
		// Check if the Authorization header is present and starts with "Bearer "
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7); // Remove "Bearer " prefix

			try {
				// Extract the email from the token
				String email = jwtUtil.extractUsername(token); // Assuming email is stored as subject in the JWT

				// Validate the token using JwtUtil
				if (jwtUtil.isTokenValid(token, email)) {
					logger.info("Received request to get all assets with valid token");
					List<Asset> assets = assetService.getAllAssets();
					logger.info("Successfully retrieved {} assets", assets.size());
					return ResponseEntity.ok(assets);
				} else {
					// If the token is invalid, return 401 Unauthorized
					logger.error("Invalid token");
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
				}
			} catch (Exception e) {
				logger.error("Failed to retrieve assets", e);
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			}
		} else {
			// If Authorization header is missing or not in the correct format
			logger.error("Authorization token is missing or invalid");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
	}

	// Method to validate the token (for example, you could use JWT validation here)
//	private boolean isValidToken(String token) {
//		// Implement your token validation logic here (e.g., JWT verification)
//		// This could involve checking the token against a signing key or verifying its
//		// expiration
//		return true; // For simplicity, assume the token is valid
//	}

	// Update asset status
	@PutMapping("/{assetId}/status")
	public ResponseEntity<String> updateAssetStatus(@PathVariable Long assetId, @RequestParam String status) {
		logger.info("Received request to update status of asset with ID: {} to {}", assetId, status);
		try {
			assetService.updateAssetStatus(assetId, status);
			logger.info("Successfully updated status of asset with ID: {} to {}", assetId, status);
			return ResponseEntity.ok("Asset status updated successfully.");
		} catch (Exception e) {
			logger.error("Failed to update status of asset with ID: {}", assetId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update asset status.");
		}
	}

	// Delete an asset
	@DeleteMapping("/{assetId}")
	public ResponseEntity<String> deleteAsset(@PathVariable Long assetId) {
		logger.info("Received request to delete asset with ID: {}", assetId);
		try {
			Asset asset = assetService.findAssetById(assetId);
			
			if (asset == null) {
				logger.info("asset not found with this id", assetId);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

			}
			assetService.deleteAsset(assetId);
			logger.info("Successfully deleted asset with ID: {}", assetId);
			return ResponseEntity.ok("Asset deleted successfully.");
		} catch (Exception e) {
			logger.error("Failed to delete asset with ID: {}", assetId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete asset.");
		}
	}

	// get an asset by id
	@GetMapping("/{assetId}")
	public ResponseEntity<Asset> GetAssetById(@PathVariable Long assetId) {
		logger.info("Received request to send the asset based on id");
		try {

			Asset foundAsset = assetService.findAssetById(assetId);
			if (foundAsset == null) {
				logger.warn("Asset not found with ID", assetId);
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
			return ResponseEntity.ok(foundAsset);

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Failed to fetch asset with ID: {}", assetId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
    @PutMapping("/{assetId}")
    public ResponseEntity<?> editAsset(@PathVariable Long assetId, @RequestBody AssetDTO assetDTO) {
        try {
            // Pass the assetId and new details to the service layer
            Asset updatedAsset = assetService.editAsset(assetId, assetDTO);
            return ResponseEntity.ok(updatedAsset); // Return the updated asset
        } catch (AssetNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating asset.");
        }
    }

	
}