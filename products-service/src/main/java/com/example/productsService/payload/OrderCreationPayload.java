package com.example.productsService.payload;

import com.example.productsService.models.CategoryOrder;
import lombok.Data;

import java.util.List;

@Data
public class OrderCreationPayload {
    private List<CategoryOrder> categoryOrders;
}
