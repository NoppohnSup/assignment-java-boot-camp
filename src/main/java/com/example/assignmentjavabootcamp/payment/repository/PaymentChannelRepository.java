package com.example.assignmentjavabootcamp.payment.repository;

import com.example.assignmentjavabootcamp.payment.entity.PaymentChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentChannelRepository extends JpaRepository<PaymentChannelEntity, Integer> {
}
