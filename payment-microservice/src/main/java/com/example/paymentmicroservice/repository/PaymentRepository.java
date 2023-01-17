package com.example.paymentmicroservice.repository;

import com.example.paymentmicroservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PaymentRepository extends JpaRepository<Payment,String> {
    List<Payment> findAllByUserId(Long id);
}
