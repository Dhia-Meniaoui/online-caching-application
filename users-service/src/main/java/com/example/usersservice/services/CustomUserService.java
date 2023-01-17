package com.example.usersservice.services;

import com.example.usersservice.exception.UnAuthorizedException;
import com.example.usersservice.models.CustomUser;
import com.example.usersservice.payload.BalanceRefreshResponse;
import com.example.usersservice.payload.request.TransactionRequestPayload;
import com.example.usersservice.repositories.CustomUserRepository;
import com.example.usersservice.security.jwt.JwtUtils;
import com.kastourik12.amqp.RabbitMQMessageProducer;
import com.kastourik12.clients.transactions.TransactionPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CustomUserService {
    private final CustomUserRepository customUserRepository;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;


    public void updateCredit( CustomUser user, Double balance) {
            user.setCredit(user.getCredit() + balance);
            customUserRepository.save(user);
    }

    public CustomUser getUser(Long id) {
        return customUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " ));
    }

    public ResponseEntity<?> UpdateCreditAndPublishTransaction(TransactionRequestPayload transactionRequest, String userId) {
        CustomUser receiver = customUserRepository.findByUsernameOrEmailOrPhone(transactionRequest.getReceiver()).orElseThrow(() -> new UnAuthorizedException("User not found"));
        CustomUser sender = customUserRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UnAuthorizedException("User not found"));
        if( sender.getId() == receiver.getId()) {
            throw new UnAuthorizedException("You can't send money to yourself");
        }
        if( sender.getCredit() < transactionRequest.getAmount() ) {
            return ResponseEntity.badRequest().body("Not enough credit");
        }
        updateCredit(sender,-transactionRequest.getAmount());
        updateCredit(receiver,transactionRequest.getAmount());
        rabbitMQMessageProducer.publish(new TransactionPayload(
                receiver.getId(),
                sender.getId(),
                transactionRequest.getAmount(),
                sender.getUsername(),
                receiver.getUsername()
                ),"transaction.exchange","users.transaction.routing-key");
        return ResponseEntity.ok("transaction done");

    }

    public ResponseEntity<?> refreshUserBalance(String userId) {
        CustomUser user = customUserRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UnAuthorizedException("User not found"));
        return ResponseEntity.ok(new BalanceRefreshResponse(user.getCredit(),user.getUsername()));
    }
}
