package com.kastourik12.clients.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor @Data @Builder @NoArgsConstructor
public class NotificationRequest{
    String message;
    String type;
    Long userId;
}
