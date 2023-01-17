package com.example.clientsservice.service;

import com.example.clientsservice.exception.CustomException;
import com.example.clientsservice.exception.UnAuthorizedException;
import com.example.clientsservice.model.Client;
import com.example.clientsservice.payload.ClientAuthPayload;
import com.example.clientsservice.repository.ClientRepository;
import com.example.clientsservice.security.JwtUtils;
import com.kastourik12.clients.clients.ClientDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientAuthService {
    private final ClientRepository clientRepository;
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> getAccessToken(ClientAuthPayload payload) {
        Client client = clientRepository.findByClientId(payload.getClientId()).orElseThrow(
                ()->new CustomException("Client not found"));
        if (!client.getSecretKey().equals(payload.getSecretKey())) {
            throw new UnAuthorizedException("Invalid credentials");
        }
        String accessToken = jwtUtils.generateJwtToken(payload.getClientId());
        return ResponseEntity.ok(accessToken);
    }

    public ResponseEntity<ClientDTO> validateAccessToken(String accessToken){
        String clientId = jwtUtils.getClientIdFromJwtToken(accessToken);
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new CustomException("Client not found"));
        return ResponseEntity.ok(new ClientDTO(client.getClientId(),client.getUserId().toString()));
    }

}
