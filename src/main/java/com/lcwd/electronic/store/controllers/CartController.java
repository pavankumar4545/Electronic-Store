package com.lcwd.electronic.store.controllers;


import com.lcwd.electronic.store.dtos.AddItemsToCartRequest;
import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")

public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("{userId}")
    public ResponseEntity<CartDto> addItemsToCart(@PathVariable String userId, @RequestBody AddItemsToCartRequest request){
        CartDto cartDto = cartService.addItemsToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }
    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemsFromCart(@PathVariable String userId,@PathVariable int itemId){
        cartService.removeItemsFromCart(userId,itemId);
        ApiResponseMessage build = ApiResponseMessage.builder()
                .message("items Deleted")
                .success(true)
                .httpStatus(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(build,HttpStatus.OK);

    }
    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        ApiResponseMessage build = ApiResponseMessage.builder().message("Cart is deleted").success(true).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(build,HttpStatus.OK);
    }
    @GetMapping("{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){
        CartDto cartByUser = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser,HttpStatus.OK);
    }

}
