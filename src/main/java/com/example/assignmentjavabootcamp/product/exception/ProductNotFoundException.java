package com.example.assignmentjavabootcamp.product.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException (String name) {
        super(name);
    }
}
