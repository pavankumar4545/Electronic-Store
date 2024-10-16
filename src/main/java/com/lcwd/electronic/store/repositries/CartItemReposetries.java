package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.entities.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemReposetries extends JpaRepository<CartItems ,Integer> {
}
