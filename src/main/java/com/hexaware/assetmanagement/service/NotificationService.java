package com.hexaware.assetmanagement.service;

import com.hexaware.assetmanagement.model.Notification;
import com.hexaware.assetmanagement.model.User;
import com.hexaware.assetmanagement.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public Notification notifyAssetRequestStatus(User user, String message, String status) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setStatus(status);
        notification.setIsRead(false);
        return notificationRepository.save(notification);
    }

    public Notification notifyAssetServiceStatus(User user, String message, String status) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setStatus(status);
        notification.setIsRead(false);
        return notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotificationsByUser(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found."));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}
