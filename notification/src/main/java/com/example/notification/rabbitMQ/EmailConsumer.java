package com.example.notification.rabbitMQ;



import com.example.notification.service.MailService;
import com.kastourik12.clients.notification.NotificationEmail;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class EmailConsumer {

    private final MailService mailService;
    @RabbitListener(queues = "${rabbitmq.queues.email}")
    public void consumer(NotificationEmail emailRequest) {
        log.info("Consumed {} from queue", emailRequest);
        this.mailService.sendMail(emailRequest);
    }
}
