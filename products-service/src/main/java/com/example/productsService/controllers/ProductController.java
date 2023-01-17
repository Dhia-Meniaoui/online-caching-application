package com.example.productsService.controllers;

import com.example.productsService.payload.CategoryUpdatingPayload;
import com.example.productsService.payload.ProductCreationPayload;
import com.example.productsService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    @GetMapping("/all")
    public ResponseEntity<?> getProducts(@RequestHeader("x-auth-client-id") String clientId){
        return productService.getAllProducts(clientId);
    }

    @GetMapping("/{id}")
    public String getProductById(@RequestHeader("x-auth-client-id") String clientId, @PathVariable String id){
        return "product";
    }
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestHeader("x-auth-user-id") String userId, @RequestBody ProductCreationPayload payload){
        return productService.addProduct(payload,userId);
    }
    @PutMapping("/update/{id}")
    public String updateProduct(@RequestHeader("x-auth-user-id") String userId, @PathVariable String id){
        return "product";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteProduct(@RequestHeader("x-auth-user-id") String userId, @PathVariable String id){
        return "product";
    }
    @PostMapping("/add-category/{id}")
    public ResponseEntity<?> addCategoryToProduct(@RequestHeader("x-auth-user-id") String clientId, @PathVariable String id,@RequestBody CategoryUpdatingPayload payload){
        return productService.addCategoryToProduct(id,clientId,payload);
    }
}
