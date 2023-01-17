package com.example.productsService.payload;


import com.example.productsService.models.Description;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class CategoryPayload {
    private String name;
    private Double price;
    private Long quantity;
    private List<Description> descriptionList;

}
