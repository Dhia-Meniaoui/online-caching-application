package com.example.clientsservice.payload;

import com.mongodb.lang.Nullable;
import lombok.Data;

@Data
public class ClientCreationPayload {
    private String applicationName;
    private String currency;
    @Nullable
    private String applicationUrl;
    private String applicationDescription;
}
