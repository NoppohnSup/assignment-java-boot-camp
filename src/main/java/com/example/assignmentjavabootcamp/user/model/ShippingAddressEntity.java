package com.example.assignmentjavabootcamp.user.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "shipping_address")
public class ShippingAddressEntity {
    @Id
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String postcode;

    @Getter
    @Setter
    private String district;

    @Getter
    @Setter
    private String province;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @Setter
    private UsersEntity users;
}
