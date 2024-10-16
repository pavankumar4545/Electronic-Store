package com.lcwd.electronic.store.controllers;


import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.ImageResponeMessage;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.service.ProductImage;
import com.lcwd.electronic.store.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@RestController
@RequestMapping("/product")
public class ProductController {

    Logger logger= LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductImage productImage;
    @Value("${product.profile.image.path}")
    private String fullPath;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto product = productService.createProduct(productDto);
        System.out.println(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id,@RequestBody ProductDto productDto ){
        ProductDto productDto1 = productService.updateProduct(productDto, id);
        return new ResponseEntity<>(productDto1,HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        ApiResponseMessage build = ApiResponseMessage.builder().message("Provided Id is deleted sucessfully").success(true).httpStatus(HttpStatus.ACCEPTED).build();
        return new ResponseEntity<>(build,HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String id){
        ProductDto singleProduct = productService.getSingleProduct(id);
        return new ResponseEntity<>(singleProduct,HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = "0",required =false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue="title",required = false) String sortBy,
            @RequestParam(value="sortDirection",defaultValue = "asc",required=false) String sortDirection
    ){
        PageableResponse<ProductDto> allProduct = productService.getAllProduct(pageNumber, pageSize, sortBy, sortDirection);
        return new ResponseEntity<>(allProduct,HttpStatus.ACCEPTED);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<ApiResponseMessage> deleteAll(){
        productService.deleteAll();
        ApiResponseMessage build = ApiResponseMessage.builder().message("All Fields are deleted").success(true).httpStatus(HttpStatus.ACCEPTED).build();
        return new ResponseEntity<>(build,HttpStatus.ACCEPTED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image/{id}")
    public ResponseEntity<ImageResponeMessage> uploadImage(@RequestParam("productImage")MultipartFile file, @PathVariable String id) throws IOException {

        String imageName = productImage.uploadImage(file, fullPath);
        logger.info("imageName {}",imageName);
        ProductDto singleProduct = productService.getSingleProduct(id);
        System.out.println(imageName);
        singleProduct.setImageName(imageName);
        ProductDto productDto = productService.updateProduct(singleProduct, id);
        ImageResponeMessage build = ImageResponeMessage.builder().fileName(imageName).success(true).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(build,HttpStatus.OK);


    }

    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @GetMapping("/image/{id}")
    public void getImage(@PathVariable String id, HttpServletResponse response) throws IOException {

        ProductDto singleProduct = productService.getSingleProduct(id);

        InputStream inputStream = productImage.viewImage(fullPath, singleProduct.getImageName());
        //ImageResponeMessage build = ImageResponeMessage.builder().message("Image fetched sucessfully").httpStatus(HttpStatus.ACCEPTED).build();
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,response.getOutputStream());



    }

}
