package com.example.productsService.controllers;


import com.example.productsService.payload.CategoryUpdatingPayload;
import com.example.productsService.service.CategoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @RequestBody CategoryUpdatingPayload payload,@RequestHeader("x-auth-user-id") String userId) {
        return  categoryService.updateCategory(id, payload, userId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id,@RequestHeader("x-auth-user-id") String userId) {
        return categoryService.deleteCategory(id, userId);
    }
    @GetMapping("{ProductId}")
    public ResponseEntity<?> getCategoriesByProductId(@PathVariable String ProductId, @RequestHeader("x-auth-user-id") String userId) {
        return categoryService.getCategoriesByProductId(ProductId, userId);
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories(@RequestHeader("x-auth-user-id") String userId) {
        return categoryService.getCategoryByUserId(userId);
    }
    @GetMapping("/client")
    public ResponseEntity<?> getCategoryById(@RequestHeader("x-auth-user-id") String userId, @QueryParam("clientId") String clientId) {
        return categoryService.getCategoryByClientId(userId, clientId);
    }

}