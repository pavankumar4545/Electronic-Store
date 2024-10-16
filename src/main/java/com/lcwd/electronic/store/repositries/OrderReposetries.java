package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.entities.Order;
import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderReposetries extends JpaRepository<Order,String> {

    public List<User> findByUser(User user);

}
