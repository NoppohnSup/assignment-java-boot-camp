package com.example.assignmentjavabootcamp.user.repository;

import com.example.assignmentjavabootcamp.user.model.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddressEntity, Integer> {
}
