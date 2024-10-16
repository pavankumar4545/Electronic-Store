package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.AddItemsToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {

    public CartDto addItemsToCart(String userId, AddItemsToCartRequest request);

    public void removeItemsFromCart(String userId,int cartItem);

    public void clearCart(String userId);

    public CartDto getCartByUser(String userId);


}
