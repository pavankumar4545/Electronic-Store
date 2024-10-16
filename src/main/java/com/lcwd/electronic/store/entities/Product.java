package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity

public class Product {
    @Id
    private String id;
    @Column(name = "product_title")
    private String title;
    @Column(name="product_description")
    private String about;
    @Column(name="product_cost")
    private int cost;

    private int quantity;
    @Column(name="Date")
    private LocalDate addedDate;
    private boolean isLive;
    private boolean stock;
    private String imageName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_Id")
    private Category category;

}





