package com.example.assignmentjavabootcamp.order.repository;

import com.example.assignmentjavabootcamp.order.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
}
