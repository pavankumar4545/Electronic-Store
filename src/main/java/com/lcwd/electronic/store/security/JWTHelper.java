package com.lcwd.electronic.store.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.*;
import java.util.function.Function;

@Component
public class JWTHelper {

    //Validate

    public static final long TOKEN_VALIDITY=5*60*60*1000;


    //This is for creating secreat key
    public static final String SECREAT_KEY="mySecretKeyIsPavankumarMadli1234567890123456789012345678901234567890";;


    public String getUserNameFromTokens(String token){
        return getClaimFromToken(token , Claims::getSubject);
    }

    //Generic Method to extract the any claim from tokens
    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(generalKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
}

public boolean isTokenExpired(String token){
      final Date expiration = getExpirationDateFromToken(token);
      return expiration.before(new Date());
}
//Retrive expiration date from JwtToken
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims=new HashMap<>();
        return dogenerateToken(claims,userDetails.getUsername());

    }

    private String dogenerateToken(Map<String,Object> claims,String subject){
        return Jwts
                .builder()
                .claims().add(claims).and()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ TOKEN_VALIDITY))
                .signWith(generalKey())
                .compact();
    }





    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(SECREAT_KEY);
        return Keys.hmacShaKeyFor(encodeKey);
    }

    }
