package com.example.clientsservice.payload;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ClientAuthPayload {
    private String clientId;
    private String secretKey;
}
