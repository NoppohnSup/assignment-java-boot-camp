package com.example.assignmentjavabootcamp.product.repository;

import com.example.assignmentjavabootcamp.product.model.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductsRepository extends JpaRepository<ProductsEntity, Integer> {
    List<ProductsEntity> findAllByNameContains(String name);
}
