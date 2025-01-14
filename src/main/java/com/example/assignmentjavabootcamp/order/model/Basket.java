package com.example.assignmentjavabootcamp.order.model;

import com.example.assignmentjavabootcamp.user.model.Users;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double totalPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @OneToOne(mappedBy = "baskets")
    private Users users;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BasketItems> basketItems = new ArrayList<>();

    public void addToBasketItems(BasketItems basketItems){
        basketItems.setBasket(this);
        this.basketItems.add(basketItems);
    }

    public void addUsers(Users users){
        users.setBaskets(this);
        this.users = users;
    }
}
