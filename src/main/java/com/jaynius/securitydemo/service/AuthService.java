
package com.jaynius.securitydemo.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jaynius.securitydemo.DTO.ReqResponse;
import com.jaynius.securitydemo.entity.AppUser;
import com.jaynius.securitydemo.repository.AppUserRepository;

@Service
public class AuthService {

    @Autowired
    private JWTutils jwTutils;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ReqResponse signup(ReqResponse registrationRequest){
        ReqResponse res=new ReqResponse();
        try {
            AppUser user=new AppUser();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());
            AppUser saveUser=appUserRepository.save(user);
            if(saveUser!=null&& saveUser.getId()>0){
                res.setAppUsers(saveUser);
                res.setMessage("user saved successfully");
                res.setStatusCode(200);
            }
            
        } catch (Exception e) {
            res.setStatusCode(500);
            res.setMessage(e.getMessage());
            
        }
        return res;
    }

    public ReqResponse signIn(ReqResponse signInRequest){
        ReqResponse response=new ReqResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                signInRequest.getEmail(), signInRequest.getPassword()));
            var user=appUserRepository.findUserByEmail(signInRequest.getEmail()).orElseThrow();
            System.out.println("the user is "+ user);
            var jwt=jwTutils.generatedToken(user);
            var refreshToken=jwTutils.generatedRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("user signed in successfully");
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public ReqResponse refreshToken(ReqResponse refreshTokenRequest){
        ReqResponse response=new ReqResponse();
        
            String userEmail=jwTutils.extractUsername(refreshTokenRequest.getEmail());
            AppUser users=appUserRepository.findUserByEmail(userEmail).orElseThrow();
            if(jwTutils.isTokenValid(refreshTokenRequest.getToken(), users)){
            var jwt =jwTutils.generatedToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenRequest.getToken());
            response.setExpirationTime("2hHr");
            response.setMessage("token refreshed successfuly ");
            }
            response.setStatusCode(500);
            return response;
       
    }
}

