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
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double totalPrice;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_order_id", nullable = false)
    private Users users;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItems> orderItems = new ArrayList<>();

    public void addToOrderItems(OrderItems orderItems){
        orderItems.setOrders(this);
        this.orderItems.add(orderItems);
    }
}
