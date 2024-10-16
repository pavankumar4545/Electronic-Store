package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.dtos.CreateOrderRequest;
import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.*;
import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositries.CartReposetries;
import com.lcwd.electronic.store.repositries.OrderItemReposetries;
import com.lcwd.electronic.store.repositries.OrderReposetries;
import com.lcwd.electronic.store.repositries.UserReposetries;
import com.lcwd.electronic.store.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderReposetries orderReposetries;
    @Autowired
    private OrderItemReposetries orderItemReposetries;
    @Autowired
    private UserReposetries userReposetries;
    @Autowired
    private CartReposetries cartReposetries;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId = orderDto.getUserId();
        String cartId = orderDto.getCartId();
        //Fetch The User
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Provided User not present please check"));

        //Fetch the Cart
        Cart cart = cartReposetries.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Provided Cart not present"));
        List<CartItems> cartItems = cart.getItems();
        if(cartItems.size() <= 0){
            throw new BadApiRequest("Invalid Number of items in cart");
        }
        Order order = Order.builder()

                .orderDate(new Date())
                .billingName(orderDto.getBillingName())
                // .orderAmount(orderDto.getOrderAmount())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderStatus(orderDto.getOrderStatus())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();


        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        //Convert CartItmes InTo OrderItems
        List<OrderItems> orderItems = cartItems.stream().map(cartItem -> {
            OrderItems orderItems1 = OrderItems.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getTotalPrice() * cartItem.getQuantity())
                    .order(order)
                    .build();

         orderAmount.set(orderAmount.get()+ orderItems1.getTotalPrice());
            return orderItems1;
        }).collect(Collectors.toList());

        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartReposetries.save(cart);
        Order savedOrder = orderReposetries.save(order);

        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderReposetries.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present"));
        orderReposetries.delete(order);


    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Provided User Not Found"));
        List<User> orders = orderReposetries.findByUser(user);
        List<OrderDto> collect = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDirection) {
        Sort sort=(sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> page = orderReposetries.findAll(pageable);

        return Helper.getPagableResponse(page,OrderDto.class);
    }
}
