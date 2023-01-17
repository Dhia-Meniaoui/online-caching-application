package com.example.productsService.repositories;

import com.example.productsService.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {


    List<Category> findAllByProductIdAndClientId(String productId, String clientId);

    List<Category> findAllByUserId(String userId);

    List<Category> findAllByClientIdAndUserId(String clientId, String userId);

    List<Category> findAllByClientId(String clientId);
}

