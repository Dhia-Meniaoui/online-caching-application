package com.example.transactionsmicroservice.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${rabbitmq.queues.transaction}")
    private String transactionQueue;
    @Value("${rabbitmq.routing-keys.users-transaction}")
    private String usersTransactionRoutingKey;
    @Value("${rabbitmq.exchanges.transaction}")
    private String transactionExchange;
    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.transactionExchange);
    }
    @Bean
    public Queue transactionQueue() {
        return new Queue(this.transactionQueue);
    }
    @Bean
    public Binding transactionBinding(){
        return BindingBuilder.
                bind(transactionQueue()).
                to(internalTopicExchange()).
                with(this.usersTransactionRoutingKey);
    }


}
