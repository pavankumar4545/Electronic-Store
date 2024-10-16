package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.dtos.AddItemsToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItems;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositries.CartItemReposetries;
import com.lcwd.electronic.store.repositries.CartReposetries;
import com.lcwd.electronic.store.repositries.ProductReposetries;
import com.lcwd.electronic.store.repositries.UserReposetries;
import com.lcwd.electronic.store.service.CartService;
import com.lcwd.electronic.store.service.UserService;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserService userService;
    @Autowired
    private ProductReposetries productReposetries;
    @Autowired
    private UserReposetries userReposetries;
    @Autowired
    private CartReposetries cartReposetries;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartItemReposetries cartItemReposetries;

    @Override
    public CartDto addItemsToCart(String userId, AddItemsToCartRequest request) {
        String productId = request.getProductId();
        int quantity = request.getQuantity();
        //Fetch the product
        Product product = productReposetries.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Provided product is not present"));
          //Fetch the user
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found please correct"));

        Cart cart = null;
        try {
          cart= cartReposetries.findByUser(user).get();
        }
        catch (NoSuchElementException ex){
            cart=new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedDate(new Date());
        }

        //If items present
        AtomicReference<Boolean> updated=new AtomicReference<>(false);
        List<CartItems> items = cart.getItems();
        items= items.stream().map(item -> {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getCost());
                updated.set(true);

            }

            return item;
        }).collect(Collectors.toList());
       // cart.setItems(items);


        if(!updated.get()){
            CartItems cartItems = CartItems.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getCost())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItems);
        }

        cart.setUser(user);
        Cart updatedCart = cartReposetries.save(cart);
        return modelMapper.map(updatedCart,CartDto.class);



    }

    @Override
    public void removeItemsFromCart(String userId, int cartItem) {
        CartItems cartItems = cartItemReposetries.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present"));
        cartItemReposetries.delete(cartItems);
    }

    @Override
    public void clearCart(String userId) {
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User id not found"));
        Cart cart = cartReposetries.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cartReposetries.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("UserId not present"));
        Cart cart = cartReposetries.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("User Not found"));


        return modelMapper.map(cart,CartDto.class);
    }
}
