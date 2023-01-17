package com.example.notification.repoistory;

import com.example.notification.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
    List<Notification> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
