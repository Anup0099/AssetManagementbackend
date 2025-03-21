package com.hexaware.assetmanagement.controller;

import com.hexaware.assetmanagement.dto.NotificationRequest;
import com.hexaware.assetmanagement.model.Notification;
import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.service.NotificationService;
import com.hexaware.assetmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Notification> notifyAssetRequestStatus(@RequestBody NotificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Notification notification = notificationService.notifyAssetRequestStatus(user, request.getMessage(), request.getStatus());
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable Long userId) {
        List<Notification> notifications = notificationService.getUnreadNotificationsByUser(userId);
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/{notificationId}/mark-read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long notificationId) {
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}
