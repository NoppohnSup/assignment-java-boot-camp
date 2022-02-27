package com.example.assignmentjavabootcamp.payment.repository;

import com.example.assignmentjavabootcamp.payment.entity.PaymentChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentChannelRepository extends JpaRepository<PaymentChannel, Integer> {
}
