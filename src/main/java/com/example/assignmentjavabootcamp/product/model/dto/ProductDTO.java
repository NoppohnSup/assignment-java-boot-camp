package com.example.assignmentjavabootcamp.product.model.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
	private int id;
	private String name;
	private String detail;
	private String brand;
	private double totalPrice;
	private double percentSalePrice;
	private int amount;
	private List<String> pictureProducts;
	private List<String> sizeProducts;
	private Timestamp saleEndDate;
}