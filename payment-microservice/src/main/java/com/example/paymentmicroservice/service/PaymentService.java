package com.example.paymentmicroservice.service;


import com.example.paymentmicroservice.exception.CustomException;
import com.example.paymentmicroservice.model.*;
import com.example.paymentmicroservice.repository.PaymentRepository;
import com.kastourik12.clients.notification.NotificationRequest;
import com.kastourik12.clients.paymentAPI.PayPalPaymentRequest;
import com.kastourik12.clients.paymentAPI.PaymentCreationResponse;
import com.kastourik12.clients.paymentAPI.PaymentDTO;
import com.kastourik12.clients.paymentAPI.PaymentBalanceHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;
import java.util.List;


@RequiredArgsConstructor
@Service
public class PaymentService {
    @Value("${go.api.host}")
    private String apiHost;
    @Value("${go.api.port}")
    private String apiPort;
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    private final AmqpPublisher amqpPublisher;


    public ResponseEntity<?> createPayment(PayPalPaymentRequest request) {
        String url = "http://" + apiHost + ":"+ apiPort + "/v1/paypal/create";
        ResponseEntity<?> response = restTemplate.postForEntity(
                url,
                request,
                PaymentCreationResponse.class);
        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
            PaymentCreationResponse paymentCreationResponse = (PaymentCreationResponse) response.getBody();
            Payment payment = new Payment();
            assert request.getUserId() != null;
            payment.setUserId(Long.parseLong(request.getUserId()));
            payment.setClientId(request.getClientId());
            payment.setType(Enum.valueOf(EType.class, request.getType()));
            payment.setId(paymentCreationResponse.getPaymentId());
            payment.setStatus(EStatus.PENDING);
            this.paymentRepository.save(payment);
            return ResponseEntity.ok(response.getBody());
        }
        return ResponseEntity.badRequest().build();
    }
    public RedirectView executePayment(String paymentId, String PayerID) {
        PaymentDTO paymentResponse = restTemplate.getForObject(
                "http://"+apiHost+":"+apiPort+"/v1/paypal/execute?paymentId="+paymentId+"&PayerID="+PayerID,
                PaymentDTO.class);
        Payment payment = this.paymentRepository.findById(paymentResponse.getPaymentId())
                .orElseThrow(
                        () -> new CustomException("Payment not found")
                );
        payment.setPayerId(paymentResponse.getPayerId());
        payment.setAmount(paymentResponse.getAmount());
        payment.setCreatedAt(Instant.now());
        payment.setProvider(MethodProvider.PAYPAL);
        payment.setStatus(EStatus.SUCCESS);
        payment.setCurrency(ECurrency.USD);
        this.paymentRepository.save(payment);
        PaymentBalanceHandler paymentBalanceHandler = new PaymentBalanceHandler(
                payment.getUserId(),
                Double.parseDouble(paymentResponse.getAmount()),
                paymentResponse.getCurrency());
        amqpPublisher.publish(paymentBalanceHandler);
        NotificationRequest notificationRequest = new NotificationRequest(
                "Payment completed successfully",
                "Payment",
                payment.getUserId()
        );
        amqpPublisher.publish(notificationRequest);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:4200/payment/success");
        return redirectView;
    }

    public ResponseEntity<?> getAllPayments(String userId) {
        List<Payment> payments = this.paymentRepository.findAllByUserId(Long.parseLong(userId));
        return ResponseEntity.ok(payments);
    }

    public ResponseEntity<?> getPaymentById(String id, String userId) {
        Payment payment = this.paymentRepository.findById(id).orElseThrow(() -> new CustomException("Payment not found"));
        if(payment.getUserId().equals(Long.parseLong(userId))) {
            return ResponseEntity.ok(payment);
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
