package com.example.notification.service;

import com.example.notification.model.Notification;
import com.example.notification.repoistory.NotificationRepository;
import com.kastourik12.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final WebSocketService webSocketService;
    public ResponseEntity<?> getAllNotification(String userId) {
        List<Notification> notifications= notificationRepository.findAllByUserIdOrderByCreatedAtDesc(Long.parseLong(userId));
        List<Notification> notifications1= new ArrayList<>();
        notifications.forEach(notification -> {
            if(notification.getStatus().equals("unread")) {
                notifications1.add(notification);
            }
        });

        return ResponseEntity.ok(notifications1.subList(0,Math.min(9,notifications1.size())));
    }



    public void saveNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setStatus("unread");
        notification.setUserId(request.getUserId());
        notification.setCreatedAt(Instant.now());
        notificationRepository.save(notification);
        webSocketService.sendNotification(request);
    }

    public ResponseEntity<?> updateNotification(String userId, List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
        return ResponseEntity.ok().build();
    }

}
