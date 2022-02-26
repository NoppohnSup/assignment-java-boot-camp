package com.example.assignmentjavabootcamp.product.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@Table(name = "products")
public class ProductsEntity {
    @Id
    private int id;
    private String name;
    private String brand;
    private String detail;
    private double totalPrice;
    private double percentSalePrice;
    private int amount;
    private Timestamp saleEndDate;
    @ElementCollection
    private List<String> pictureProducts;
    @ElementCollection
    private List<String> sizeProducts;
}
