package com.example.assignmentjavabootcamp.user.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UsersDTO {
    private int id;
    private String firstName;
    private String lastName;
    private List<ShippingAddressDTO> shippingAddress;
}
