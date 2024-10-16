package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDtos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryService  {

    //create
    public CategoryDto createCategory(CategoryDto categoryDto);


    //update
    public CategoryDto updateCategory(CategoryDto categoryDto,String id);


    //delete
    public void deleteDto(String id);

    //getSingle
    public CategoryDto getSingleCategory(String id);

    //getAll
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String sortBy,String sortDirection);

    //Serch
   // public UserDtos serchCategory(String keyword);





}
