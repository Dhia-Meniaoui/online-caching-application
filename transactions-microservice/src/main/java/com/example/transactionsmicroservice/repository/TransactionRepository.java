package com.example.transactionsmicroservice.repository;

import com.example.transactionsmicroservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllBySenderOrReceiver(Long userId, Long userId1);
}
