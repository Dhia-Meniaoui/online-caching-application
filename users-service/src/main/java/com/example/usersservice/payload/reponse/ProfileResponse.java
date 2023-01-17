package com.example.usersservice.payload.reponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfileResponse {
    private String name;
    private String credit;
    private String currency;
}
