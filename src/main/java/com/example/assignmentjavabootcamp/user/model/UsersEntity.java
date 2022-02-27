package com.example.assignmentjavabootcamp.user.model;

import com.example.assignmentjavabootcamp.order.model.Basket;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UsersEntity {
    @Id
    private int id;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "users")
    private List<ShippingAddressEntity> shippingAddress = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket baskets;
}
