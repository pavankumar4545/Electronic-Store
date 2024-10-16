package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.OrderItems;
import com.lcwd.electronic.store.entities.User;
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
public class OrderDto {


    private String orderId;
    private String orderStatus="Pending";
    private String paymentStatus="Not Paid";
    private int orderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date orderDate;
    private Date deliveredDate;
   // private UserDtos user;
    private List<OrderItemsDto> orderItems=new ArrayList<>();


}
