package com.example.assignmentjavabootcamp.order.model.dto;

import com.example.assignmentjavabootcamp.product.model.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class OrderItemDTO {
	private int id;
	private int qty;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private ProductDTO product;
}