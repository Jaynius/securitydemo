package com.jaynius.securitydemo.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JWTutils {

    private SecretKey key;

    private static final long EXPIRATION_TIME=86400000;

    public JWTutils(){
        String secretKey="39394938482746265472842648294274777773284822774782784673643827478276737647834";
        byte[] keybyte=Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
        this.key=new SecretKeySpec(keybyte, "HmacSHA256");
    }


    public String generatedToken(UserDetails userDetails){
        return Jwts.builder()
         .subject(userDetails.getUsername())
         .issuedAt(new Date(System.currentTimeMillis()))
         .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
         .signWith(key)
         .compact();
    }


    public String generatedRefreshToken(HashMap<String,Object> claims, UserDetails userDetails){
        return Jwts.builder()
         .claims(claims)
         .subject(userDetails.getUsername())
         .issuedAt(new Date(System.currentTimeMillis()))
         .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
         .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(Jwts
        .parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload());
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUsername(token);
        return username.equals(userDetails.getUsername())&& !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}