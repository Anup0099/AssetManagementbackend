package com.hexaware.assetmanagement.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.assetmanagement.model.ServiceRequest;

public interface IServiceRequestService {
    ServiceRequest submitServiceRequest(ServiceRequest serviceRequest);
    Optional<ServiceRequest> findServiceRequestsByUserId(Long userId);
    void updateServiceRequestStatus(Long requestId, String status);
}
