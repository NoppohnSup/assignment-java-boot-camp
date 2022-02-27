package com.example.assignmentjavabootcamp.payment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "payment_channel")
public class PaymentChannel {
    @Id
    private int id;
    private String name;
}
