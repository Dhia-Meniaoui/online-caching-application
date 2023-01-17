package com.example.paymentmicroservice.controller;

import com.example.paymentmicroservice.model.EType;
import com.example.paymentmicroservice.service.PaymentService;
import com.kastourik12.clients.paymentAPI.PayPalPaymentRequest;
import lombok.RequiredArgsConstructor;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/payment/")
@RequiredArgsConstructor
public class PaymentController {
    private Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    @PostMapping("create")
    public ResponseEntity<?> createPayment(@RequestBody PayPalPaymentRequest request,@RequestHeader("X-auth-user-id") String userId) {
        logger.info("inside Payment microserivce : creating payment request");
        if(request.getUserId() == null || request.getUserId().isEmpty()){
            request.setUserId(userId);
        }
        if(request.getType() == null || request.getType().isEmpty()){
            request.setType(EType.USER_INCOME.name());
        }
        if(request.getRedirectUri() == null || request.getRedirectUri().isEmpty()){
            request.setRedirectUri("http://localhost:8082/payment/execute");
        }
        if(request.getClientId() == null || request.getClientId().isEmpty()){
            request.setClientId("sb");
        }
        if(request.getCancelUri() == null || request.getCancelUri().isEmpty()){
            request.setCancelUri("http://localhost:8082/payment/cancel");
        }
        return this.paymentService.createPayment(request);
    }
    @GetMapping("execute")
    public RedirectView executePayment(@RequestParam String paymentId, @RequestParam String PayerID){
        logger.info("inside Payment microserivce : executing created payment ");
        return this.paymentService.executePayment(paymentId,PayerID);
    }
    @GetMapping("all")
    public ResponseEntity<?> getAllPayments(@RequestHeader("x-auth-user-id") String userId){
        logger.info("inside Payment microserivce : getting all payments ");
        return this.paymentService.getAllPayments(userId);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPaymentById(@PathVariable String id,@RequestHeader("x-auth-user-id") String userId){
        logger.info("inside Payment microserivce : getting payment by id ");
        return this.paymentService.getPaymentById(id,userId);
    }

}
