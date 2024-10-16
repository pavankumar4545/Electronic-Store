package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;

import java.util.List;

public interface OrderService {

    //Create Order
    public OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    public void removeOrder(String orderId);


    //getOrdersOfUser
    public List<OrderDto> getOrdersOfUser(String userId);

    //Get Orders
    public PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDirection);



}
