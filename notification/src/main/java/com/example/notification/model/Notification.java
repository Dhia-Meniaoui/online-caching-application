package com.example.notification.model;


import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@Document
@Data
public class Notification {
    @Id
    private String id;
    private String message;
    private String type;
    private String status;
    @CreatedDate
    private Instant createdAt;
    private Long userId;
}
