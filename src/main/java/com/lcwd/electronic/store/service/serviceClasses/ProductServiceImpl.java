package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.Category;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositries.CategoryReposetries;
import com.lcwd.electronic.store.repositries.ProductReposetries;
import com.lcwd.electronic.store.service.ProductService;
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
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductReposetries reposetries;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${product.profile.image.path}")
    private String fullPathOfImage;

    @Autowired
    private CategoryReposetries categoryReposetries;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        String id= UUID.randomUUID().toString();
        productDto.setId(id);
        Product product = dtoToEntity(productDto);
        Product save = reposetries.save(product);
        ProductDto productDto1 = entityToDto(save);



        return productDto1;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, String id) {
        Product product = reposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("Provided Id id not present please check once"));
        product.setAbout(productDto.getAbout());
        product.setTitle(productDto.getTitle());
        product.setLive(productDto.isLive());
        product.setCost(productDto.getCost());
        product.setStock(productDto.isStock());
        product.setAddedDate(productDto.getAddedDate());
        product.setQuantity(productDto.getQuantity());
        product.setImageName(productDto.getImageName());

        Product save = reposetries.save(product);
        ProductDto productDto1 = entityToDto(save);
        return productDto1;
    }

    @Override
    public void deleteProduct(String id) {
        Product product = reposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present please check once"));
       String path=fullPathOfImage+ File.separator+product.getImageName();
       try{
          Path paths = Paths.get(path);
           Files.delete(paths);
       } catch (IOException e) {


       }

        reposetries.delete(product);

    }

    @Override
    public ProductDto getSingleProduct(String id) {
        Product product = reposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present please check"));
        ProductDto productDto = entityToDto(product);
        return productDto;
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize,String sortBy,String sortDirection) {
       Sort sort=(sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageble= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = reposetries.findAll(pageble);
        PageableResponse<ProductDto> pagableResponse = Helper.getPagableResponse(all, ProductDto.class);
        return pagableResponse;
    }

    @Override
    public void deleteAll() {
        reposetries.deleteAll();

    }

    @Override
    public ProductDto createCategory(ProductDto productDto, String id) {
//        Category category = categoryReposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found !!"));
//        Product product = modelMapper.map(productDto, Product.class);
//
//        //product id
//        String productId = UUID.randomUUID().toString();
//        product.setId(productId);
//        //added
//        //  product.setAddedDate(new Date());
//        product.setCategory(category);
//        Product saveProduct = reposetries.save(product);
//        return modelMapper.map(saveProduct, ProductDto.class);


        Category category = categoryReposetries.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id is not present"));

       // category.setCategoryId(categoryID);
        Product product = dtoToEntity(productDto);
        String categoryID = UUID.randomUUID().toString();
        product.setId(categoryID);
        product.setCategory(category);
        Product save = reposetries.save(product);
        ProductDto productDto1 = entityToDto(save);

        return productDto1;
    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        Product product = reposetries.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Provided Id id not Present please check"));
        Category category = categoryReposetries.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present"));
        product.setCategory(category);
        Product save = reposetries.save(product);

        return modelMapper.map(save,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllCategoryWithProduct(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDirection) {
        Sort sort=(sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending());

        Category category = categoryReposetries.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Provided category Id is not present"));

       Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> byCategory = reposetries.findByCategory(category,pageable);
        return Helper.getPagableResponse(byCategory,ProductDto.class);



    }

    public ProductDto entityToDto(Product product){
        return modelMapper.map(product,ProductDto.class);
    }
    public Product dtoToEntity(ProductDto productDto){
        return modelMapper.map(productDto,Product.class);
    }
}
