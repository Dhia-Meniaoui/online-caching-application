package com.example.clientsservice.controller;

import com.example.clientsservice.payload.ClientCreationPayload;
import com.example.clientsservice.payload.ClientUpdatingPayload;
import com.example.clientsservice.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;


@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@RequestHeader("x-auth-user-id") String userId,
                                          @RequestBody ClientCreationPayload payload,
                                          @RequestHeader("x-auth-user-login") String username) {
        return clientService.createClient(userId,payload,username);
    }
    @PutMapping("/update/{clientId}")
    public ResponseEntity<?> updateClient(@RequestHeader("x-auth-user-id") String userId,@PathVariable String clientId,@RequestBody ClientUpdatingPayload payload)  {
        return clientService.updateClient(userId,clientId,payload);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllClients(@RequestHeader("x-auth-user-id") String userId)  {
        return clientService.getAllClients(userId);
    }
    @GetMapping()
    public ResponseEntity<?> getClient(@RequestHeader("x-auth-user-id") String userId,@QueryParam("clientId") String clientId)  {
        return clientService.getClient(userId,clientId);
    }

}
