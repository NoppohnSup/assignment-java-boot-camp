package com.example.assignmentjavabootcamp.user.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShippingAddressDTO {
    private int id;
    private String address;
    private String postcode;
    private String district;
    private String province;
}
