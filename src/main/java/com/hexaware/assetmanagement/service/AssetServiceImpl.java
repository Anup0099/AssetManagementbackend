package com.hexaware.assetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.exception.AssetNotFoundException;
import com.hexaware.assetmanagement.exception.ResourceNotFoundException;
import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.repository.AssetRepository;
import com.hexaware.assetmanagement.repository.BorrowedAssetRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class AssetServiceImpl implements IAssetService {

	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	private BorrowedAssetRepository borrowedAssetRepository;

	@Override
	public Asset addAsset(Asset asset) {
		return assetRepository.save(asset);
	}

	@Override
	public Asset findAssetById(Long assetId) {
		return assetRepository.findById(assetId)
				.orElseThrow(() -> new ResourceNotFoundException("Asset not found with ID: " + assetId));
	}

	@Override
	public List<Asset> getAllAssets() {
		return assetRepository.findAll();
	}

	@Override
	public void updateAssetStatus(Long assetId, String status) {
		Asset asset = findAssetById(assetId);
		asset.setStatus(status);
		assetRepository.save(asset);
	}

	@Override
	public void deleteAsset(Long assetId) {
		Asset asset = findAssetById(assetId);
		borrowedAssetRepository.deleteByAssetId(assetId);
		assetRepository.delete(asset);
	}
	
	 public Asset editAsset(Long assetId, AssetDTO assetDTO) {
	        // Fetch the existing asset from the database
	        Asset asset = assetRepository.findById(assetId)
	                .orElseThrow(() -> new AssetNotFoundException("Asset with ID " + assetId + " not found."));

	        // Update the asset's fields with values from the DTO
	        asset.setAssetName(assetDTO.getAssetName());
	        asset.setCategory(assetDTO.getCategory());
	        asset.setDescription(assetDTO.getDescription());
	        asset.setStatus(assetDTO.getStatus());

	        // Save the updated asset to the database
	        return assetRepository.save(asset);
	    }
}