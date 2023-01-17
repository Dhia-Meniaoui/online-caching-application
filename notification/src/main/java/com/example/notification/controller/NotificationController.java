package com.example.notification.controller;


import com.example.notification.model.Notification;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/notif")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestHeader("X-auth-user-id") String userId) {
        return notificationService.getAllNotification(userId);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestHeader("X-auth-user-id") String userId, @RequestBody List<Notification> notifications) {
        return notificationService.updateNotification(userId, notifications);
    }
}
