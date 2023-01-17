package com.kastourik12.amqp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQMessageProducer {
    private final AmqpTemplate amqpTemplate;
    public void publish(Object payload, String exchange, String routingKey) {
        log.info("Publishing message to exchange {} with routing key {}", exchange, routingKey);
        amqpTemplate.convertAndSend(exchange, routingKey, payload);
    }
}
