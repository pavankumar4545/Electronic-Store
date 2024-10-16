package com.lcwd.electronic.store.entities;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity

public class OrderItems {

    @Id
    private int orderItemId;
    private int quantity;
    private int totalPrice;
    @OneToOne
    @JoinColumn(name="product_Id")
    private Product product;
    @ManyToOne
    @JoinColumn(name="order_Id")
    private Order order;

}
