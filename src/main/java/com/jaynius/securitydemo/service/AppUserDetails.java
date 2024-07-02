package com.jaynius.securitydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jaynius.securitydemo.repository.AppUserRepository;

@Service
public class AppUserDetails implements UserDetailsService{

    @Autowired
    private AppUserRepository appUserRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       return appUserRepository.findUserByEmail(username).orElseThrow();
    }

}
