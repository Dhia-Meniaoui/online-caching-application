package com.example.transactionsmicroservice.payload;


import lombok.Data;

@Data
public class TransactionRequest {

    private String receiver;

    private Double amount;

}
