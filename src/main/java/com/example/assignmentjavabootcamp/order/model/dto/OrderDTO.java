package com.example.assignmentjavabootcamp.order.model.dto;

import com.example.assignmentjavabootcamp.user.model.dto.UsersDTO;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class OrderDTO {
	private int id;
	private double totalPrice;
	private UsersDTO user;
	private List<OrderItemDTO> orderItems;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}