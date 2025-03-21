package com.hexaware.assetmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.model.ServiceRequest;
import com.hexaware.assetmanagement.repository.ServiceRequestRepository;

@Service
public class ServiceRequestServiceImpl implements IServiceRequestService {

    @Autowired
    private ServiceRequestRepository serviceRequestRepository;

    @Override
    public ServiceRequest submitServiceRequest(ServiceRequest serviceRequest) {
        return serviceRequestRepository.save(serviceRequest);
    }

    @Override
    public Optional<ServiceRequest> findServiceRequestsByUserId(Long userId) {
        return serviceRequestRepository.findById(userId);
    }

    @Override
    public void updateServiceRequestStatus(Long requestId, String status) {
        ServiceRequest request = serviceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Service Request not found with ID: " + requestId));
        request.setStatus(status);
        serviceRequestRepository.save(request);
    }
}
