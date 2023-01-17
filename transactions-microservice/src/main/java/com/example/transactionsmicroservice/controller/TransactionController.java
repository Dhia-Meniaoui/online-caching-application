package com.example.transactionsmicroservice.controller;

import com.example.transactionsmicroservice.model.Transaction;
import com.example.transactionsmicroservice.payload.TransactionRequest;
import com.example.transactionsmicroservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions(@RequestHeader("x-auth-user-id") String userId) {
        return transactionService.getAllTransactions(userId);
    }



}
