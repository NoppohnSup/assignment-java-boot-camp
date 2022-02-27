package com.example.assignmentjavabootcamp.order.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddBasketRequest {
    private Integer productId;
    private Integer userId;
    private Integer qty;
}
