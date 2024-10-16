package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.CartItems;
import com.lcwd.electronic.store.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CartDto {

    private String cartId;


    private Date createdDate;


    private UserDtos user;


    private List<CartItemsDto> items=new ArrayList<>();



}
