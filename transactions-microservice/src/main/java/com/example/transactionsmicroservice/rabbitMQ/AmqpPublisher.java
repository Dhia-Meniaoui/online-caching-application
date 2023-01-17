package com.example.transactionsmicroservice.rabbitMQ;

import com.kastourik12.amqp.RabbitMQMessageProducer;
import com.kastourik12.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AmqpPublisher {
    @Value("${rabbitmq.exchanges.notification}")
    private String notificationExchange;
    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String notificationRoutingKey;
    private final RabbitMQMessageProducer rabbitMqMessageProducer;

    public void publish(NotificationRequest notificationRequest) {
        rabbitMqMessageProducer.publish(notificationRequest, this.notificationExchange, this.notificationRoutingKey);
    }
}


