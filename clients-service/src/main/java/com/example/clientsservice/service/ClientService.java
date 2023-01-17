package com.example.clientsservice.service;

import com.example.clientsservice.exception.CustomException;
import com.example.clientsservice.model.Client;
import com.example.clientsservice.model.ECurrency;
import com.example.clientsservice.payload.ClientCreationPayload;
import com.example.clientsservice.payload.ClientAuthPayload;
import com.example.clientsservice.payload.ClientUpdatingPayload;
import com.example.clientsservice.repository.ClientRepository;
import com.example.clientsservice.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final JwtUtils jwtUtils;




    public ResponseEntity<?> createClient(String userId, ClientCreationPayload payload,String username) {
        logger.info("Creating client {}", payload);
            String generateString = jwtUtils.generateJwtToken(username);
            String clientId = generateString.split("\\.")[2];
            generateString = jwtUtils.generateJwtToken(clientId);
            String secretKey = generateString.split("\\.")[2];
            clientId.replace("-","");
            secretKey.replace("-","");
            Client client =  new Client();
                    client.setClientId(clientId);
                    client.setUserId(Long.parseLong(userId));
                    client.setApplicationName(payload.getApplicationName());
                    client.setApplicationDescription(payload.getApplicationDescription());
                    client.setApplicationUrl(payload.getApplicationUrl());
                    client.setCurrency(ECurrency.valueOf(payload.getCurrency()));
                    client.setSecretKey(secretKey);
            clientRepository.save(client);
            return ResponseEntity.ok("Client saved successfully");
    }
    public ResponseEntity<?> updateClient(String userId, String clientId, ClientUpdatingPayload payload) {
        logger.info("Updating client {}", payload);
        Client client = clientRepository.findById(clientId).orElseThrow(()->new RuntimeException("Client not found"));
        if (!client.getUserId().equals(Long.parseLong(userId))) {
            return ResponseEntity.badRequest().build();
        }
        client.setApplicationName(payload.getApplicationName());
        client.setApplicationUrl(payload.getApplicationUrl());
        client.setApplicationDescription(payload.getApplicationDescription());
        client.setCurrency(ECurrency.valueOf(payload.getCurrency()));
        return ResponseEntity.ok(clientRepository.save(client));
    }

    public ResponseEntity<?> getAllClients(String userId) {
        logger.info("Getting all clients for user {}", userId);
        List<ClientAuthPayload> clients = new ArrayList<>();

        return ResponseEntity.ok(clientRepository.findAllByUserId(Long.parseLong(userId)));
    }

    public ResponseEntity<?> getClient(String userId, String clientId) {
        logger.info("Getting client {} for user {}", clientId, userId);
        Client client = clientRepository.searchFirstByClientId(clientId).orElseThrow(()->new CustomException("Client not found"));
        if (!client.getUserId().equals(Long.parseLong(userId))) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(client);
    }
}

