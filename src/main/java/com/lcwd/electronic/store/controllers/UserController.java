package com.lcwd.electronic.store.controllers;


import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.ImageResponeMessage;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDtos;
import com.lcwd.electronic.store.service.ImageService;
import com.lcwd.electronic.store.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    @Value("${user.profile.image.path}")
    private String filePath;

   // @PreAuthorize("hasRole('NORMAL')")
    @PostMapping
    public ResponseEntity<UserDtos> createUser(@Valid @RequestBody UserDtos userDtos){
        UserDtos user = userService.createUser(userDtos);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('NORMAL')")
@PutMapping("/{id}")
    public ResponseEntity<UserDtos> updateUser(@Valid @PathVariable("id") String id, @RequestBody UserDtos userDtos){
        UserDtos userDtos1 = userService.updateUser(userDtos, id);
        return new ResponseEntity<>(userDtos1,HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String id ){
        userService.deleteUser(id);
        ApiResponseMessage message = ApiResponseMessage.builder().message("User is deleted sucessfully ").success(true).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK) ;
    }

    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    @GetMapping
    public ResponseEntity<PageableResponse<UserDtos>> getAllUser(
            @RequestParam (value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam (value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required = false)String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc",required = false)String sortDirection
    ){

        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDirection),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','NORMAL')")
    @GetMapping("{id}")
    public ResponseEntity<UserDtos> getSingleUser(@PathVariable String id){
        UserDtos userById = userService.getUserById(id);
        return  new ResponseEntity<>(userById,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDtos> getUserByEmail(@PathVariable String email){
        UserDtos userByEmail = userService.getUserByEmail(email);
        return new ResponseEntity<>(userByEmail,HttpStatus.OK);

    }

    @PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDtos>> getByKeyword(@PathVariable String keyword){
        List<UserDtos> userDtos = userService.searchUser(keyword);
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('NORMAL')")
    @PostMapping("/image/{id}")
    public ResponseEntity<ImageResponeMessage> uploadFiles( @RequestParam("userImage") MultipartFile file,@PathVariable String id) throws IOException {

        String imageName = imageService.uploadFile(file, filePath);

        UserDtos userById = userService.getUserById(id);
        userById.setImageName(imageName);
        UserDtos userDtos = userService.updateUser(userById, id);

        ImageResponeMessage message=ImageResponeMessage.builder().fileName(imageName).success(true).httpStatus(HttpStatus.CREATED).build();

         return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','NORMAL'")
    @GetMapping("/image/{id}")
    public void fileDownload(@PathVariable String id, HttpServletResponse response) throws IOException {
        UserDtos userById = userService.getUserById(id);
        logger.info("FileName is {}",userById.getImageName());
        InputStream reSource = imageService.getReSource(filePath, userById.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(reSource,response.getOutputStream());




    }

}
