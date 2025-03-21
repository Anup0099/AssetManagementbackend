package com.hexaware.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.assetmanagement.model.Notification;
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query("SELECT n FROM Notification n WHERE n.user.userId = :userId AND n.isRead = false")
    List<Notification> findByUserIdAndIsReadFalse(@Param("userId") Long userId);
}
