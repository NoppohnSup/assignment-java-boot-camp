package com.example.assignmentjavabootcamp.order.model;

import com.example.assignmentjavabootcamp.product.model.Products;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class BasketItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int qty;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Products products;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;
}
