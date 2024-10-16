package com.lcwd.electronic.store.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {

    private String cartId;
    private String userId;
    private String orderStatus="Pending";
    private String paymentStatus="Not Paid";
    private String billingAddress;
    private String billingPhone;
    private String billingName;



}
