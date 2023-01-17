package com.example.clientsservice.controller;

import com.example.clientsservice.payload.ClientAuthPayload;
import com.example.clientsservice.service.ClientAuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client-auth")
public class ClientAuthController {
    private final ClientAuthService clientAuthService;
    private Logger logger = LoggerFactory.getLogger(ClientAuthController.class);
    @PostMapping("/get-access-token")
    public ResponseEntity<?> getAccessToken(@RequestBody ClientAuthPayload payload) {
        logger.info("inside clients service : getting access token");
        return clientAuthService.getAccessToken(payload);
    }
    @GetMapping("/validateToken")
    public ResponseEntity<?> validateAccessToken( @RequestParam("token") String accessToken){
        logger.info("inside clients service : validating access token");
        return clientAuthService.validateAccessToken(accessToken);
    }
}
