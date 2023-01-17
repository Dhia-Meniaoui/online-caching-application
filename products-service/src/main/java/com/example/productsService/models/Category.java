package com.example.productsService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("category")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Category {
    @Id
    private String id;
    private String name;
    private Double price;
    private Long quantity;
    private String image;
    private String clientId;
    private String userId;
    private List<Description> descriptions;
    @DBRef @JsonIgnore
    private Product product;
    public Category(String name,Double price, Long quantity, String image, List<Description> descriptions, Product product) {
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.descriptions = descriptions;
        this.product = product;
    }
}
