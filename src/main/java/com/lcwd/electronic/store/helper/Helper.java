package com.lcwd.electronic.store.helper;

import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.UserDtos;
import com.lcwd.electronic.store.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    public static <U,V> PageableResponse<V> getPagableResponse(Page<U> page,Class<V> type){

        List<U> content = page.getContent();
        // List<User> all = userReposetries.findAll();
        List<V> collect = content.stream().map(object -> new ModelMapper().map(object,type)).collect(Collectors.toList());

        PageableResponse<V> response=new PageableResponse<>();
        response.setContent(collect);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLastPage(page.isLast());
        return response;

//        List<U> content = page.getContent();
//        content.stream().map(object-> new ModelMapper().map(object,V));
//
//        PageableResponse<V> response=new PageableResponse<>();
//        response.setContent();

    }
}
