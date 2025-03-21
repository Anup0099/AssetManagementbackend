package com.hexaware.assetmanagement.repository;

import com.hexaware.assetmanagement.model.AssetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetRequestRepository extends JpaRepository<AssetRequest, Long> {
//	@Query("SELECT r FROM AssetRequest r " +
//		       "JOIN FETCH r.asset a " +
//		       "JOIN FETCH r.user u " +
//		       "LEFT JOIN FETCH u.userDetails d " +
//		       "WHERE r.status = :status")
//		List<AssetRequest> findByStatusWithDetails(@Param("status") String status);
//	@Query("SELECT r FROM AssetRequest r WHERE r.status = :status")
//	List<AssetRequest> findByStatus(@Param("status") String status);
	@Query("SELECT ar FROM AssetRequest ar JOIN FETCH ar.user u LEFT JOIN FETCH u.userDetails WHERE ar.status = :status")
	List<AssetRequest> findByStatusWithUserDetails(@Param("status") String status);
	List<AssetRequest> findByUser_UserId(Long userId);





}
