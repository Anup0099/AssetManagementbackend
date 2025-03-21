package com.hexaware.assetmanagement.service;

import java.util.List;
import java.util.Optional;

import com.hexaware.assetmanagement.model.BorrowedAsset;

public interface IBorrowedAssetService {
    BorrowedAsset borrowAsset(BorrowedAsset borrowedAsset);
    List<BorrowedAsset> findBorrowedAssetsByUserId(Long userId);
	Optional<List<BorrowedAsset>> getAllBorrowedAsset();
	Optional<BorrowedAsset> findById(Long borrowId);
	public List<BorrowedAsset> getAllBorrowedAssets();


	
}