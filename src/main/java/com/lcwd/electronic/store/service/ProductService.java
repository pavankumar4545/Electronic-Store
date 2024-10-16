package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;

public interface ProductService {

    //create
    public ProductDto createProduct(ProductDto productDto);

    //update

    public ProductDto updateProduct(ProductDto productDto ,String id);

    //delete
    public void deleteProduct(String id);

    //single
    public ProductDto getSingleProduct(String id);


    //getAll
    public PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDirection);

    //deleteAll
    public void deleteAll();


    public ProductDto createCategory(ProductDto productDto,String id);



    public ProductDto updateCategory(String productId,String categoryId);



    PageableResponse<ProductDto> getAllCategoryWithProduct(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDirection);
}
