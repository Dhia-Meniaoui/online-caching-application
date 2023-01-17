package com.example.usersservice.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class BalanceRefreshResponse {
    private Double credit;
    private String username;
}
