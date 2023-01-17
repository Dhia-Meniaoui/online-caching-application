package com.example.clientsservice.payload;

import lombok.Data;

@Data
public class ClientUpdatingPayload {
    private String currency;
    private String applicationName;
    private String applicationUrl;
    private String applicationDescription;
}
