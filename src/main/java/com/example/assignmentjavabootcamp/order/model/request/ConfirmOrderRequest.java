package com.example.assignmentjavabootcamp.order.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfirmOrderRequest {
    private int userId;
    private int basketId;
}
