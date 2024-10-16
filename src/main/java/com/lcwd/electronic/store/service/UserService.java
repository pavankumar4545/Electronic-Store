package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDtos;

import java.util.List;

public interface UserService {

    public UserDtos createUser(UserDtos userDtos);
    public UserDtos updateUser(UserDtos userDtos,String userId);
    public void deleteUser(String userId);
    public PageableResponse<UserDtos> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDirection);
    public UserDtos getUserById(String userId);
    public UserDtos getUserByEmail(String userEmail);
    public List<UserDtos> searchUser(String keyword);

}
