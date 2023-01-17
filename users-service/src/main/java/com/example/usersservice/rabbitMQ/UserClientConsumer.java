package com.example.usersservice.rabbitMQ;

import com.example.usersservice.controllers.AuthController;
import com.example.usersservice.models.CustomUser;
import com.example.usersservice.services.CustomUserService;
import com.kastourik12.clients.paymentAPI.PaymentBalanceHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserClientConsumer {
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final CustomUserService customUserService;
    @RabbitListener(queues = "${rabbitmq.queues.payment}")
    public void consumer(PaymentBalanceHandler payment) {
        logger.info("Consumed {} from queue", payment);
        CustomUser customUser = customUserService.getUser(payment.getUserId());
        customUserService.updateCredit(customUser,payment.getAmount());
    }
}
