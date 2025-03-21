package com.hexaware.assetmanagement.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.dto.BorrowedAssetDTO;
import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.model.BorrowedAsset;
import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.repository.AssetRepository;
import com.hexaware.assetmanagement.repository.BorrowedAssetRepository;
import com.hexaware.assetmanagement.repository.UserRepository;

@Service
public class BorrowedAssetServiceImpl implements IBorrowedAssetService {

	@Autowired
	private BorrowedAssetRepository borrowedAssetRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AssetRepository assetRepository;

	@Override
	public BorrowedAsset borrowAsset(BorrowedAsset borrowedAsset) {
		Optional<User> user = userRepository.findById(borrowedAsset.getUser().getUserId());
		Optional<Asset> asset = assetRepository.findById(borrowedAsset.getAsset().getAssetId());
		User user1 = user.get();
		Asset asset1 = asset.get();

		borrowedAsset.setUser(user1);
		borrowedAsset.setAsset(asset1);
		if (borrowedAsset.getAsset().getAssetId() == null) {
			throw new IllegalArgumentException("Asset ID cannot be null.");
		}
		return borrowedAssetRepository.save(borrowedAsset);
	}

	
	@Override
	public Optional<List<BorrowedAsset>> getAllBorrowedAsset() {

		return Optional.ofNullable(borrowedAssetRepository.findAll());
	}
	@Override
	public List<BorrowedAsset> findBorrowedAssetsByUserId(Long userId) {
	    return borrowedAssetRepository.findByUser_UserId(userId);
	}
	
	@Override
	public Optional<BorrowedAsset> findById(Long borrowId) {
        return borrowedAssetRepository.findById(borrowId);
    }
	
	@Override
	public List<BorrowedAsset> getAllBorrowedAssets() {
	    return borrowedAssetRepository.findAll();
	}

	


}
