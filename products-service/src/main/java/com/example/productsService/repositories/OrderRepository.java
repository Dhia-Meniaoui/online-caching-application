package com.example.productsService.repositories;

import com.example.productsService.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
    Order findByPaymentId(String paymentId);
}
