package com.hexaware.assetmanagement.service;



import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dto.AssetRequestDTO;
import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.model.AssetRequest;
import com.hexaware.assetmanagement.model.BorrowedAsset;
import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.repository.AssetRepository;
import com.hexaware.assetmanagement.repository.AssetRequestRepository;
import com.hexaware.assetmanagement.repository.BorrowedAssetRepository;
import com.hexaware.assetmanagement.repository.UserRepository;

@Service
public class AssetRequestService {

    @Autowired
    private AssetRequestRepository assetRequestRepository;

    @Autowired
    private BorrowedAssetRepository borrowedAssetRepository;
    
    @Autowired
    private AssetRepository assetRepository; // Add AssetRepository to validate assets
    
    @Autowired
    private UserRepository userRepository;

    public AssetRequest createRequest(AssetRequest request) {
        // Log the received request for debugging
      

        // Validate the asset and user fields
        if (request.getAsset() == null || request.getAsset().getAssetId() == null) {
            throw new IllegalArgumentException("Asset ID is required.");
        }
        if (request.getUser() == null || request.getUser().getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        // Fetch the asset and user from the database
        Asset asset = assetRepository.findById(request.getAsset().getAssetId())
                .orElseThrow(() -> new IllegalArgumentException("Asset not found with ID: " + request.getAsset().getAssetId()));
        User user = userRepository.findById(request.getUser().getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUser().getUserId()));

        // Set the asset and user objects
        request.setAsset(asset);
        request.setUser(user);

        // Set the request date if not provided
        if (request.getRequestDate() == null) {
            request.setRequestDate(LocalDate.now());
        }

        // Save the request and return it
        return assetRequestRepository.save(request);
    }


//    public AssetRequest createRequest(AssetRequest request) {
//        request.setStatus("Pending");
//        request.setRequestDate(LocalDate.now());
//        return assetRequestRepository.save(request);
//    }

    public List<AssetRequestDTO> getPendingRequests() {
    	List<AssetRequest> requests = assetRequestRepository.findByStatusWithUserDetails("Pending");


        // Debugging logs
        requests.forEach(request -> {
            System.out.println("Asset Request ID: " + request.getRequestId());
            System.out.println("Asset Name: " + request.getAsset().getAssetName());
            System.out.println("User Details: " + (request.getUser().getUserDetails() != null ? request.getUser().getUserDetails().getName() : "No User Details"));
        });

        return requests.stream()
                .map(request -> new AssetRequestDTO(
                        request.getRequestId(),
                        request.getAsset().getAssetName(),
                        request.getUser().getUserDetails() != null ? request.getUser().getUserDetails().getName() : "Unknown User",
                        request.getRequestDate()
                ))
                .collect(Collectors.toList());
    }





    public Optional<AssetRequest> getRequestById(Long requestId) {
        return assetRequestRepository.findById(requestId);
    }

    public AssetRequest updateRequestStatus(Long requestId, String status) {
        Optional<AssetRequest> optionalRequest = assetRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            AssetRequest request = optionalRequest.get();
            request.setStatus(status);
            assetRequestRepository.save(request);

            if (status.equalsIgnoreCase("Accepted")) {
                // Move to BorrowedAssets
                BorrowedAsset borrowedAsset = new BorrowedAsset();
                borrowedAsset.setAsset(request.getAsset());
                borrowedAsset.setUser(request.getUser());
                borrowedAsset.setBorrowDate(LocalDate.now());
                borrowedAsset.setStatus("Borrowed");

                borrowedAssetRepository.save(borrowedAsset);
            }
            return request;
        }
        throw new RuntimeException("Request not found");
    }
    public List<AssetRequestDTO> getRequestsByUserId(Long userId) {
        // Fetch asset requests from the repository
        List<AssetRequest> requests = assetRequestRepository.findByUser_UserId(userId);

        // Transform AssetRequest entities to AssetRequestDTOs
        return requests.stream()
                .map(request -> new AssetRequestDTO(
                        request.getRequestId(),
                        request.getAsset().getAssetName(),
                        request.getUser().getUserDetails().getName(),
                        request.getRequestDate()))
                .collect(Collectors.toList());
    }
}
