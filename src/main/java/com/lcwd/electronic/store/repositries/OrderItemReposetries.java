package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.entities.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemReposetries extends JpaRepository<OrderItems,Integer> {

}
