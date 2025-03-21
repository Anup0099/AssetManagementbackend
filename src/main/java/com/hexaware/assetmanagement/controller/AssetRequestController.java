package com.hexaware.assetmanagement.controller;



import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.assetmanagement.dto.AssetRequestDTO;
import com.hexaware.assetmanagement.model.AssetRequest;
import com.hexaware.assetmanagement.repository.AssetRepository;
import com.hexaware.assetmanagement.repository.AssetRequestRepository;
import com.hexaware.assetmanagement.repository.UserRepository;
import com.hexaware.assetmanagement.service.AssetRequestService;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:3000")
public class AssetRequestController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AssetRequestService assetRequestService;
    
    @Autowired
    private AssetRequestRepository assetRequestRepository;

    @Autowired
    private AssetRepository assetRepository;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<AssetRequest> createRequest(@RequestBody AssetRequest request) {
        logger.info("Received AssetRequest: {}", request);
        try {
            AssetRequest savedRequest = assetRequestService.createRequest(request);
            return ResponseEntity.ok(savedRequest);
        } catch (IllegalArgumentException e) {
            logger.error("Error creating request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            logger.error("Unexpected error creating request", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




    @GetMapping("/pending")
    public ResponseEntity<List<AssetRequestDTO>> getPendingRequests() {
        return ResponseEntity.ok(assetRequestService.getPendingRequests());
    }

 
    @PutMapping("/{requestId}")
    public ResponseEntity<AssetRequest> updateRequestStatus(
            @PathVariable Long requestId,
            @RequestParam String status) {
        return ResponseEntity.ok(assetRequestService.updateRequestStatus(requestId, status));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AssetRequestDTO>> getRequestsByUserId(@PathVariable Long userId) {
        List<AssetRequestDTO> requests = assetRequestService.getRequestsByUserId(userId);
        if (requests.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(requests);
    }
}
