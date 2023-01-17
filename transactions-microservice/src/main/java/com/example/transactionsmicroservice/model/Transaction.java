package com.example.transactionsmicroservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data @Builder
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JsonIgnore
    Long receiver;
    @JsonIgnore
    Long sender;
    Double amount;
    String currency;
    String senderName;
    String receiverName;
    @CreatedDate
    Instant createdAt;
}
