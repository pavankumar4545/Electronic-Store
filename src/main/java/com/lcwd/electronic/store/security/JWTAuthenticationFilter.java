package com.lcwd.electronic.store.security;

import com.lcwd.electronic.store.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    Logger logger= LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    @Autowired
    private JWTHelper jwtHelper;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //authorization: Bearer XSGGSHHHSH
        //Extact the authorization header
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header {}",requestHeader);


        String userName=null;

        String token=null;

        //Checks if header contains JWT Token
        if(requestHeader !=null && requestHeader.startsWith("Bearer ")){
            token=requestHeader.substring(7);

            //Extract Username From Token
            try{
                 userName = jwtHelper.getUserNameFromTokens(token);
                 logger.info("userName {}",userName);


            }
            catch (IllegalArgumentException ex){
                logger.info("Token is not correct {}",ex.getMessage());
            }
            catch (ExpiredJwtException e){
                logger.info("Jwt is expired{}",e.getMessage());
            }
            catch (Exception exception){
                exception.printStackTrace();
            }


        }
        else{
            logger.info("Invalid Header ");
        }

        //Chechs If userName is Valid
        if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails1 = userDetailsService.loadUserByUsername(userName);

            if(userName.equals(userDetails1.getUsername()) && !jwtHelper.isTokenExpired(token)){


                UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails1,null,userDetails1.getAuthorities());
                  authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


        }
        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        // Exclude Swagger URLs
        return path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") || path.startsWith("/webjars");
    }

}
