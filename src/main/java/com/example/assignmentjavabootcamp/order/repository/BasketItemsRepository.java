package com.example.assignmentjavabootcamp.order.repository;

import com.example.assignmentjavabootcamp.order.model.Basket;
import com.example.assignmentjavabootcamp.order.model.BasketItems;
import com.example.assignmentjavabootcamp.product.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketItemsRepository extends JpaRepository<BasketItems, Integer> {
    Optional<BasketItems> findByProductsAndBasket(Products products, Basket basket);

    void deleteByBasketId(Integer id);
}
