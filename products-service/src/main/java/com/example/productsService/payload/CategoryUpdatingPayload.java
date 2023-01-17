package com.example.productsService.payload;

import com.example.productsService.models.Description;
import feign.Client;
import lombok.Data;

import java.util.List;

@Data
public class CategoryUpdatingPayload {
    private String name;
    private Double price;
    private Long quantity;
    private String image;
    private List<Description> descriptions;
    private String clientId;
}
