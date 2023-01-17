package com.example.usersservice.payload.request;

import lombok.Data;

@Data
public class TransactionRequestPayload {
    String receiver;
    Double amount;
}
