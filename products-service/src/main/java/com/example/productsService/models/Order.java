package com.example.productsService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("order")
@Data @Builder
public class Order {
    @Id
    private String id;
    private List<CategoryOrder> categoryOrders;
    private Double totalPrice;
    private EStatus status;
    @JsonIgnore
    private String clientId;
    @JsonIgnore
    private String paymentId;
    @JsonIgnore
    private String payerId;
}
