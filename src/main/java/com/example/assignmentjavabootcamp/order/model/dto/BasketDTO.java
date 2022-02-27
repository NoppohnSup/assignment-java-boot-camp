package com.example.assignmentjavabootcamp.order.model.dto;

import java.sql.Timestamp;
import java.util.List;

import com.example.assignmentjavabootcamp.user.model.dto.UsersDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketDTO{
	private int id;
	private double totalPrice;
	private UsersDTO user;
	private List<BasketItemDTO> basketItems;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}