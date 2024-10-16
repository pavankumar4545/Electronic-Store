package com.lcwd.electronic.store.controllers;


import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.service.CategoryImage;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
public class CategoryController {

     Logger logger= LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryImage categoryImage;

    @Value("${category.profile.image.path}")
    private String filePath;

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);

    }
    @PutMapping("{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") String id,@RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.updateCategory(categoryDto, id);
        return new ResponseEntity<>(categoryDto1,HttpStatus.CREATED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable("id") String id){
        categoryService.deleteDto(id);
        ApiResponseMessage build = ApiResponseMessage.builder().message("User Is Deleted Succesfully").success(true).httpStatus(HttpStatus.ACCEPTED).build();
        return new ResponseEntity<>(build,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAllCategory(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDirection",defaultValue = "asc",required = false) String sortDirection
    ){
       // PageableResponse<CategoryDto> allCategory = categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(categoryService.getAllCategory(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("id") String id){
        CategoryDto singleCategory = categoryService.getSingleCategory(id);
        return new ResponseEntity<>(singleCategory,HttpStatus.ACCEPTED);
    }
    @PostMapping("/image/{id}")
    public ResponseEntity<ImageResponeMessage> uploadImage(@RequestParam("userImage")MultipartFile file,@PathVariable String id) throws IOException {
        String imageName = categoryImage.uploadImage(file, filePath);
        CategoryDto singleCategory = categoryService.getSingleCategory(id);
        singleCategory.setCoverImage(imageName);
        CategoryDto categoryDto = categoryService.updateCategory(singleCategory, id);
        ImageResponeMessage build = ImageResponeMessage.builder().fileName(imageName).success(true).httpStatus(HttpStatus.ACCEPTED).build();
        return new ResponseEntity<>(build,HttpStatus.ACCEPTED);

    }
    @GetMapping("/image/{id}")
    public void viewImage(@PathVariable String id, HttpServletResponse response) throws IOException {

        CategoryDto singleCategory = categoryService.getSingleCategory(id);
        logger.info("categoryName {}",singleCategory.getCoverImage());
        InputStream inputStream = categoryImage.viewImage(filePath, singleCategory.getCoverImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());


    }
    @PostMapping("{id}/product")
    public ResponseEntity<ProductDto> createProductWithCategory(@PathVariable("id") String id,@RequestBody ProductDto productDto){
        ProductDto category = productService.createCategory(productDto, id);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateProductWithCategory(@PathVariable String categoryId ,@PathVariable String productId){

        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/product")
    public ResponseEntity<PageableResponse<ProductDto>> getAllCategoryWithProduct(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy" ,defaultValue = "title",required = false) String sortBy,
            @RequestParam(value="sortDirection",defaultValue = "asc",required = false) String sortDirection

    ){
        PageableResponse<ProductDto> allCategoryWithProduct = productService.getAllCategoryWithProduct(categoryId, pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(allCategoryWithProduct,HttpStatus.OK);


    }

}
