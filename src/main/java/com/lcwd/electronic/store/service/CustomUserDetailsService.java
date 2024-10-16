package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.repositries.UserReposetries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
   private UserReposetries userReposetries;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       return userReposetries.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("No username is present"));
    }
}
