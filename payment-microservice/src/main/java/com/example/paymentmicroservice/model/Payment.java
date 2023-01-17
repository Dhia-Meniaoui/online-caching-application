package com.example.paymentmicroservice.model;

import brave.internal.Nullable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;


@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    private String id;
    private String amount;
    @JsonIgnore
    private String payerId;
    @CreationTimestamp
    private Instant createdAt;
    @JsonIgnore
    private Long userId;
    @Enumerated(EnumType.STRING)
    private ECurrency currency;
    @JsonIgnore
    private String clientId;
    @Enumerated(EnumType.STRING)
    private MethodProvider provider;
    @Nullable
    private EType type;
    private EStatus status;


}