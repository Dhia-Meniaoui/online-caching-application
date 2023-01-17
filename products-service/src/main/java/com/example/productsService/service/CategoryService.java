package com.example.productsService.service;

import com.example.productsService.exception.CustomException;
import com.example.productsService.models.Category;
import com.example.productsService.payload.CategoryUpdatingPayload;
import com.example.productsService.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.OverridesAttribute;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public Category getCategory(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CustomException("Category not found"));
    }
    public ResponseEntity<?> updateCategory(String id, CategoryUpdatingPayload payload, String userId) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CustomException("Category not found"));
        if (!category.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        category.setPrice(payload.getPrice());
        category.setQuantity(payload.getQuantity());
        category.setImage(payload.getImage());
        categoryRepository.save(category);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> deleteCategory(String id, String userId) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CustomException("Category not found"));
        if (!category.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        categoryRepository.delete(category);
        return ResponseEntity.ok().build();
    }
    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public ResponseEntity<?> getCategoriesByProductId(String productId, String clientId) {

    return ResponseEntity.ok(categoryRepository.findAllByProductIdAndClientId(productId, clientId));
    }

    public ResponseEntity<?> getCategoryByUserId(String userId) {
        return ResponseEntity.ok(this.categoryRepository.findAllByUserId(userId));
    }
    public ResponseEntity<?> getCategoryByClientId(String userId, String clientId) {
        List<Category> products = this.categoryRepository.findAllByClientIdAndUserId(clientId,userId);
        return ResponseEntity.ok(products);
    }
    public List<Category> getCategoryByClientId(String clientId) {
        return this.categoryRepository.findAllByClientId(clientId);
    }
}
