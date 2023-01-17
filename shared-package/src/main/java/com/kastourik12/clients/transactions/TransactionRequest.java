package com.kastourik12.clients.transactions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TransactionRequest {
    private String receiver;
    private Long sender;
    private Double amount;
}
