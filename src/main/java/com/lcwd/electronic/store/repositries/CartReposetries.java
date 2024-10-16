package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartReposetries extends JpaRepository<Cart,String> {


    public Optional<Cart> findByUser(User user);
}
