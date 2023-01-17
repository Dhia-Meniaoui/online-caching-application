package com.example.notification.service;

import com.kastourik12.clients.notification.NotificationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {
    private final SimpMessagingTemplate template;
    @Autowired
    public WebSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendNotification(NotificationRequest request) {
        template.convertAndSend("/topic/notification", "Notification: " + request.getMessage());
    }

}
