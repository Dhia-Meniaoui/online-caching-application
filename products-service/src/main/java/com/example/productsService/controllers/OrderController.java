package com.example.productsService.controllers;

import com.example.productsService.payload.OrderCreationPayload;
import com.example.productsService.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderCreationPayload payload, @RequestHeader("x-auth-client-id") String clientId, @RequestHeader("x-auth-user-id") String userId,@RequestHeader("x-auth-client-uri") String clientUri){
        return orderService.createOrder(payload,clientId,userId,clientUri);
    }
    @PostMapping("/execute")
    public ResponseEntity<?> executeOrder( @RequestParam String paymentId, @RequestParam String PayerId){
        return orderService.ExecuteApprovedOrder(paymentId,PayerId);
    }


}
