package com.example.assignmentjavabootcamp.order.repository;

import com.example.assignmentjavabootcamp.order.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Integer> {
    Optional<Basket> findByUsersId(Integer users);
}
