package com.lcwd.electronic.store.controllers;


import com.lcwd.electronic.store.config.SecurityConfig;
import com.lcwd.electronic.store.dtos.JWTRequest;
import com.lcwd.electronic.store.dtos.JWTResponse;
import com.lcwd.electronic.store.dtos.RefereshTokenDto;
import com.lcwd.electronic.store.dtos.UserDtos;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.security.JWTHelper;
import com.lcwd.electronic.store.service.CustomUserDetailsService;
import com.lcwd.electronic.store.service.RefereshService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RefereshService refereshService;

    private Logger logger= LoggerFactory.getLogger(SecurityConfig.class);

    @PostMapping("/generateToken")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request){
        logger.info("UserName {} Password {}",request.getEmail(),request.getPassword());
        this.doAuthentication(request.getEmail(),request.getPassword());

        User user = (User)userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtHelper.generateToken(user);

        //Refresh Token Service
        RefereshTokenDto token1 = refereshService.createRefreshToken(user.getEmail());

        JWTResponse build1 = JWTResponse.builder()
                .token(token)
                .userDtos(modelMapper.map(user, UserDtos.class))
                .refereshTokenDto(token1)
                .build();


        JWTResponse build = JWTResponse.builder().token(token).userDtos(modelMapper.map(user, UserDtos.class)).build();

        return ResponseEntity.ok(build);

    }

    private void doAuthentication(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadCredentialsException("Invalid credentials");
        }
    }



}






