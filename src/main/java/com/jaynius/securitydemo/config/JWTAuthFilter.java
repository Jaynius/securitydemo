package com.jaynius.securitydemo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jaynius.securitydemo.service.AppUserDetails;
import com.jaynius.securitydemo.service.JWTutils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTutils jwTutils;

    @Autowired
    private AppUserDetails appUserDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader=request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        if(authHeader==null || authHeader.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }

        jwtToken=authHeader.substring(7);
        userEmail=jwTutils.extractUsername(jwtToken);
        if (userEmail !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails=appUserDetails.loadUserByUsername(userEmail);
            if(jwTutils.isTokenValid(jwtToken, userDetails)){
                SecurityContext securityContext=SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken( userDetails,null,userDetails.getAuthorities());

                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
    }


}
