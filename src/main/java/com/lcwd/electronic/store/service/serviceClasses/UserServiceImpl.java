package com.lcwd.electronic.store.service.serviceClasses;

import com.lcwd.electronic.store.config.ProjectConfig;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDtos;
import com.lcwd.electronic.store.entities.Roles;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositries.RolesReposetries;
import com.lcwd.electronic.store.repositries.UserReposetries;
import com.lcwd.electronic.store.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserReposetries userReposetries;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String userPath;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesReposetries rolesReposetries;

    @Override
    public UserDtos createUser(UserDtos userDtos) {
        String string = UUID.randomUUID().toString();
        userDtos.setUserId(string);
        User user =dtoToEntity(userDtos);
        //Password encoder spring security
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Roles  roles1=new Roles();
        roles1.setRoleId(UUID.randomUUID().toString());
        roles1.setRoleName("ROLE_NORMAL");
        Roles roles = rolesReposetries.findByRoleName("ROLE_NORMAL").orElse(roles1);
        user.setRoles(List.of(roles));

        User save = userReposetries.save(user);
       UserDtos dtos= entityToDto(save);
        return dtos;
    }


    @Override
    public UserDtos updateUser(UserDtos userDtos, String userId) {
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present"));
       // user.setUserId(userDtos.getUserId());
        user.setName(userDtos.getName());
        user.setEmail(userDtos.getEmail());
        user.setPassword(userDtos.getPassword());
        user.setAbout(userDtos.getAbout());
        user.setImageName(userDtos.getImageName());
        user.setGender(userDtos.getGender());

        User save = userReposetries.save(user);
        UserDtos userDtos1 = entityToDto(save);


        return userDtos1;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present"));
        String fullPath = userPath + File.separator + user.getImageName();
        System.out.println("Trying to delete file: " + fullPath);
        try {
            Path path= Paths.get(fullPath);
            Files.delete(path);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userReposetries.delete(user);

    }

    @Override
    public PageableResponse<UserDtos> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDirection) {

        Sort sort = (sortDirection.equalsIgnoreCase("desc")) ? (Sort.by(sortBy)).descending() :(Sort.by(sortBy)).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<User> page = userReposetries.findAll(pageable);
        List<User> content = page.getContent();
        // List<User> all = userReposetries.findAll();
        List<UserDtos> collect = content.stream().map(user -> entityToDto(user)).collect(Collectors.toList());

        PageableResponse<UserDtos> response=new PageableResponse<>();
        response.setContent(collect);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public UserDtos getUserById(String userId) {
        User user = userReposetries.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Provided Id is not present"));
        UserDtos userDtos = entityToDto(user);
        return userDtos;
    }

    @Override
    public UserDtos getUserByEmail(String userEmail) {
        User byEmail = userReposetries.findByEmail(userEmail).orElseThrow(()-> new ResourceNotFoundException("Given Email is not Present"));
        UserDtos userDtos = entityToDto(byEmail);
        return userDtos;
    }

    @Override
    public List<UserDtos> searchUser(String keyword) {
        List<User> byNameContaining = userReposetries.findByNameContaining(keyword);
        List<UserDtos> collect = byNameContaining.stream().map(name -> entityToDto(name)).collect(Collectors.toList());
        return collect;
    }

    private UserDtos entityToDto(User save) {

       // UserDtos build = UserDtos.builder()
//                .userId(save.getUserId())
//                .name(save.getName())
//                .password(save.getPassword())
//                .about(save.getAbout())
//                .email(save.getEmail())
//                .gender(save.getGender())
//                .imageName(save.getImageName())
//                .build();


        return modelMapper.map(save,UserDtos.class);
    }

    private User dtoToEntity(UserDtos userDtos) {
//      User user=  User.builder()
//                .userId(userDtos.getUserId())
//                .name(userDtos.getName())
//                .password(userDtos.getPassword())
//                .about(userDtos.getAbout())
//                .email(userDtos.getEmail())
//                .gender(userDtos.getGender())
//                .imageName(userDtos.getImageName())
//                .build();
        return modelMapper.map(userDtos,User.class);
    }


}
