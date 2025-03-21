package com.hexaware.assetmanagement.service;

import java.util.List;

import com.hexaware.assetmanagement.dto.AssetDTO;
import com.hexaware.assetmanagement.model.Asset;

public interface IAssetService {
    Asset addAsset(Asset asset);
    Asset findAssetById(Long assetId);
    List<Asset> getAllAssets();
    void updateAssetStatus(Long assetId, String status);
    void deleteAsset(Long assetId);
	Asset editAsset(Long assetId, AssetDTO assetDTO);
    
	
}