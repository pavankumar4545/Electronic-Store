package com.lcwd.electronic.store.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="categories_Table")
public class Category {
    @Id
    @Column(name="category_Id")
    private String categoryId;
    @Column(name="category_title")
    private String title;
    @Column(name="category_Description")
    private String description;
    @Column(name="category_coverImage")
    private String coverImage;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product> products=new ArrayList<>();
}
