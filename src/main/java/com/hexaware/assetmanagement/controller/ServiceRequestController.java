package com.hexaware.assetmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.assetmanagement.model.ServiceRequest;
import com.hexaware.assetmanagement.service.IServiceRequestService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceRequestController.class);

    @Autowired
    private IServiceRequestService serviceRequestService;

    // 1. Submit a new service request
    @PostMapping
    public ResponseEntity<ServiceRequest> submitServiceRequest(@RequestBody ServiceRequest serviceRequest) {
        logger.info("Received request to submit a new service request: {}", serviceRequest);
        ServiceRequest createdRequest = serviceRequestService.submitServiceRequest(serviceRequest);
        logger.info("Successfully created service request: {}", createdRequest);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    // 2. Find service requests by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<ServiceRequest>> getServiceRequestsByUserId(@PathVariable Long userId) {
        logger.info("Received request to fetch service requests for user ID: {}", userId);
        Optional<ServiceRequest> serviceRequests = serviceRequestService.findServiceRequestsByUserId(userId);
        if (serviceRequests.isPresent()) {
            logger.info("Service requests found for user ID {}: {}", userId, serviceRequests);
            return new ResponseEntity<>(serviceRequests, HttpStatus.OK);
        } else {
            logger.warn("No service requests found for user ID: {}", userId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 3. Update service request status
    @PutMapping("/{requestId}/status")
    public ResponseEntity<String> updateServiceRequestStatus(
            @PathVariable Long requestId, 
            @RequestParam String status) {
        logger.info("Received request to update status for service request ID: {} with status: {}", requestId, status);
        try {
            serviceRequestService.updateServiceRequestStatus(requestId, status);
            logger.info("Successfully updated status for service request ID: {} to {}", requestId, status);
            return new ResponseEntity<>("Service request status updated successfully.", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating status for service request ID: {}. Error: {}", requestId, e.getMessage());
            return new ResponseEntity<>("Error updating service request status: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
