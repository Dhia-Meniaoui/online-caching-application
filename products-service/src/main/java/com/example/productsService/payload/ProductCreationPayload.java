package com.example.productsService.payload;

import com.example.productsService.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.List;


@Data @AllArgsConstructor @NoArgsConstructor
public class ProductCreationPayload {
    private String name;
    private List<CategoryPayload> products;
    private String clientId;
}
