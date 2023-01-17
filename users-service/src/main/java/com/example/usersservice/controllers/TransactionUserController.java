package com.example.usersservice.controllers;

import com.example.usersservice.payload.request.TransactionRequestPayload;
import com.example.usersservice.services.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("send")
@RequiredArgsConstructor
public class TransactionUserController {
    private final CustomUserService customUserService;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @PostMapping("/transactionRequest")
    public ResponseEntity<?> getSenderAndReceiver(@RequestBody TransactionRequestPayload transactionRequest,@RequestHeader("x-auth-user-id") String userId) {
        logger.info("inside users service: getting balance");
        return this.customUserService.UpdateCreditAndPublishTransaction(transactionRequest,userId);
    }
    @GetMapping("/balance")
    public ResponseEntity<?> refreshUserBalance(@RequestHeader("x-auth-user-id") String userId) {
        return this.customUserService.refreshUserBalance(userId);
    }



}
