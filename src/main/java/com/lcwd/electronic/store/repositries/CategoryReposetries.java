package com.lcwd.electronic.store.repositries;


import com.lcwd.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryReposetries extends JpaRepository<Category,String> {
}
