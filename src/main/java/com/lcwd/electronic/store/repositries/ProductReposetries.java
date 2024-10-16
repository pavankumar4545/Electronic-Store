package com.lcwd.electronic.store.repositries;

import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReposetries extends JpaRepository<Product,String> {


    public Page<Product> findByCategory(Category category, Pageable pageable);

}
