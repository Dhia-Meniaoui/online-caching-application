package com.example.transactionsmicroservice.rabbitMQ;

import com.example.transactionsmicroservice.service.TransactionService;
import com.kastourik12.clients.notification.NotificationRequest;
import com.kastourik12.clients.transactions.TransactionPayload;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Consumer {
    private Logger logger = LoggerFactory.getLogger(Consumer.class);
    private final AmqpPublisher amqpPublisher;
    private final TransactionService transactionService;
    @RabbitListener(queues = "${rabbitmq.queues.transaction}")
    public void consumer(TransactionPayload transactionPayload) {
        logger.info("Consumed {} from queue", transactionPayload);
        transactionService.createTransaction(transactionPayload);
        amqpPublisher.publish(new
                NotificationRequest(
                "You received a new transaction from " + transactionPayload.getSenderName(),
                "Transaction",
                transactionPayload.getReceiver()
                )
        );
        amqpPublisher.publish(new
                NotificationRequest(
                "You sent a new transaction to " + transactionPayload.getReceiverName(),
                "Transaction",
                transactionPayload.getSender()
                )
        );
    }
}
