package com.example.productsService.controllers;


import com.example.productsService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client-product")
public class ClientController {
    private final ProductService productService;
    @GetMapping("/all")
    public ResponseEntity<?> getProducts(@RequestHeader("x-auth-client-id") String clientId){
        return productService.getAllProducts(clientId);
    }

    @GetMapping("/{id}")
    public String getProductById(@RequestHeader("x-auth-client-id") String clientId, @PathVariable String id){
        return "product";
    }

}
