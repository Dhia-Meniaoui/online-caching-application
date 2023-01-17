package com.example.usersservice.payload.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
@Builder
public class JwtResponse {
	private String token;
	private Date expiresAt;
	private String username;
	private Double balance;
}
