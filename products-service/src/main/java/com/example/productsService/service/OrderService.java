package com.example.productsService.service;

import com.example.productsService.exception.CustomException;
import com.example.productsService.exception.UnAuthorizedException;
import com.example.productsService.models.Category;
import com.example.productsService.models.EStatus;
import com.example.productsService.models.Order;
import com.example.productsService.payload.OrderCreationPayload;
import com.example.productsService.repositories.OrderRepository;
import com.kastourik12.clients.paymentAPI.PayPalPaymentRequest;
import com.kastourik12.clients.paymentAPI.PaymentCreationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final CategoryService categoryService;
    @Transactional
    public ResponseEntity<?> createOrder(OrderCreationPayload payload,String clientId,String userId,String clientUrl) {

        AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
        payload.getCategoryOrders().forEach(categoryOrder -> {
            Category category = categoryService.getCategory(categoryOrder.getCategoryId());
            if (category.getQuantity() < categoryOrder.getQuantity()){
                throw new CustomException("Not enough quantity");
            }
            if(!category.getClientId().equals(clientId)){
                throw new UnAuthorizedException("You are not allowed to buy this product");
            }
            totalPrice.updateAndGet(v -> v + category.getPrice() * categoryOrder.getQuantity());
        });
        PayPalPaymentRequest request = new PayPalPaymentRequest();
        request.setTotal(totalPrice.get().toString());
        request.setCurrency("USD");
        request.setClientId(clientId);
        request.setType("USER_INCOME");
        request.setUserId(userId);
        request.setRedirectUri("http://localhost:8085/order/execute/"+clientId);
        request.setCancelUri(clientUrl + "/cancel");
        try {
            ResponseEntity<?> response = restTemplate.postForEntity("http://payment-microservice/payment/create",request, PaymentCreationResponse.class);
            if(response.getStatusCode().is2xxSuccessful()){
                Order order = Order.builder()
                        .categoryOrders(payload.getCategoryOrders())
                        .totalPrice(totalPrice.get())
                        .status(EStatus.PENDING)
                        .build();
                orderRepository.save(order);
                return ResponseEntity.ok(response);
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @Transactional
    public ResponseEntity<?> ExecuteApprovedOrder(String paymentId,String PayerId){
        try {
            ResponseEntity<?> response = restTemplate.getForEntity("http://payment-microservice/payment/execute?paymentId="+paymentId+"&PayerId="+PayerId, String.class);
            if(response.getStatusCode().is2xxSuccessful()){
                Order order = orderRepository.findByPaymentId(paymentId);
                order.setStatus(EStatus.APPROVED);
                order.setPayerId(PayerId);
                orderRepository.save(order);
                return ResponseEntity.ok(response.getBody());
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}

