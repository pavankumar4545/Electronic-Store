package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReposetries extends JpaRepository<User,String> {


    public Optional<User> findByEmail(String email);

    public List<User> findByNameContaining(String keyword);

}
