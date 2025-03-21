package com.hexaware.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.assetmanagement.model.BorrowedAsset;

@Repository
public interface BorrowedAssetRepository extends JpaRepository<BorrowedAsset, Long> {
	
	 List<BorrowedAsset> findByUser_UserId(Long userId);
	 @Modifying
	 @Query("DELETE FROM BorrowedAsset b WHERE b.asset.assetId = :assetId")
	 void deleteByAssetId(@Param("assetId") Long assetId);


}
