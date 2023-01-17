package com.example.transactionsmicroservice.service;

import com.example.transactionsmicroservice.controller.TransactionController;
import com.example.transactionsmicroservice.model.Transaction;
import com.example.transactionsmicroservice.repository.TransactionRepository;
import com.kastourik12.clients.transactions.TransactionPayload;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private final TransactionRepository transactionRepository;


    @Transactional
    public void createTransaction(TransactionPayload transactionPayload) {
        logger.info("Creating transaction {}", transactionPayload);
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionPayload.getAmount());
        transaction.setSender(transactionPayload.getSender());
        transaction.setReceiver(transactionPayload.getReceiver());
        transaction.setSenderName(transactionPayload.getSenderName());
        transaction.setReceiverName(transactionPayload.getReceiverName());
        transactionRepository.save(transaction);
    }

    public ResponseEntity<?> getAllTransactions(String userId) {
        logger.info("Getting all transactions for user {}", userId);
        List<Transaction> transactions = transactionRepository.findAllBySenderOrReceiver(Long.parseLong(userId),Long.parseLong(userId));
        List<Transaction> transactions1 = new ArrayList<>();
        for(Transaction transaction : transactions){
            if (transaction.getSender().equals(Long.parseLong(userId))) {
                transaction.setSenderName("You");
            } else {
                transaction.setReceiverName("You");
            }
            transactions1.add(transaction);
            }
        return ResponseEntity.ok(transactions1);
        }

}

