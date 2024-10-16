package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.dtos.CategoryDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositries.CategoryReposetries;
import com.lcwd.electronic.store.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
   private CategoryReposetries reposetries;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${category.profile.image.path}")
    private String path;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
     String id= UUID.randomUUID().toString();
     categoryDto.setCategoryId(id);
        Category category = dtoToEntity(categoryDto);
        Category save = reposetries.save(category);

        CategoryDto categoryDto1 = entityToDto(save);



        return categoryDto1;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, String id) {
        Category category = reposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present please chech Id"));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        Category save = reposetries.save(category);
        CategoryDto categoryDto1 = entityToDto(save);
        return categoryDto1;
    }

    @Override
    public void deleteDto(String id) {
        Category category = reposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Id present pleae check provided id"));
        String fullPath=path+ File.separator+category.getCoverImage();
        try{
           Path paths= Paths.get(fullPath);
            Files.delete(paths);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        reposetries.delete(category);


    }

    @Override
    public CategoryDto getSingleCategory(String id) {
        Category category = reposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("No id is present please check once"));
        CategoryDto categoryDto = entityToDto(category);
        return categoryDto;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber,int pageSize,String sortBy,String sortDirection) {
        Sort sort=(sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()): (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Category> page = reposetries.findAll(pageable);
        PageableResponse<CategoryDto> pagableResponse = Helper.getPagableResponse(page, CategoryDto.class);


        return pagableResponse;
    }

    public CategoryDto entityToDto(Category category){

        return modelMapper.map(category,CategoryDto.class);
    }

    public Category dtoToEntity(CategoryDto categoryDto){

        return modelMapper.map(categoryDto,Category.class);
    }


}
