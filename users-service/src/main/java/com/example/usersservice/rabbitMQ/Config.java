package com.example.usersservice.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${rabbitmq.exchanges.users}")
    private String usersExchange;
    @Value("${rabbitmq.queues.payment}")
    private String paymentQueue;
    @Value("${rabbitmq.queues.transaction}")
    private String transactionQueue;
    @Value("${rabbitmq.routing-keys.users-payment}")
    private String usersPaymentRoutingKey;
    @Value("${rabbitmq.routing-keys.users-transaction}")
    private String usersTransactionRoutingKey;

    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.usersExchange);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(this.paymentQueue);
    }

    @Bean Queue transactionQueue() {
        return new Queue(this.transactionQueue);
    }
    @Bean
    public Binding transactionBinding(){
        return BindingBuilder.
                bind(transactionQueue()).
                to(internalTopicExchange()).
                with(this.usersTransactionRoutingKey);
    }

    @Bean
    public Binding usersPaymentBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(internalTopicExchange())
                .with(this.usersPaymentRoutingKey);
    }
}
