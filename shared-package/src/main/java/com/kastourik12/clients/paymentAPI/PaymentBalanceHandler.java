package com.kastourik12.clients.paymentAPI;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class PaymentBalanceHandler{
    private Long userId;
    private Double amount;
    private String currency;
}
