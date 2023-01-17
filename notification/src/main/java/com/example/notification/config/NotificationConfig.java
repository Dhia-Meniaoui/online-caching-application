package com.example.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {
    @Value("${rabbitmq.exchanges.notification}")
    private String internalExchange;

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.queues.email}")
    private String emailQueue;

    @Value("${rabbitmq.routing-keys.notification}")
    private String notificationRoutingKey;
    @Value("${rabbitmq.routing-keys.email}")
    private String emailRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(this.notificationQueue);
    }
    @Bean
    public Queue emailQueue() {
        return new Queue(this.emailQueue);
    }
    @Bean
    public Binding emailBiding(){
        return BindingBuilder.bind(emailQueue())
                .to(internalTopicExchange())
                .with(this.emailRoutingKey);
    }

    @Bean
    public Binding NotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.notificationRoutingKey);
    }

}
