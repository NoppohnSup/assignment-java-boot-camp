package com.example.assignmentjavabootcamp.product.repository;

import com.example.assignmentjavabootcamp.product.model.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity, Integer> {
    List<ProductsEntity> findAllByNameContains(String name);
}
