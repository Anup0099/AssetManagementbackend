package com.hexaware.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.assetmanagement.model.ServiceRequest;

@Repository
public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
}

