package com.example.productsService.models;

import lombok.Data;

@Data
public class CategoryOrder {
    private String CategoryId;
    private Long quantity;
    private Double price;
}
