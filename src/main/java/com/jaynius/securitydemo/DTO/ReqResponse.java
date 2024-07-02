package com.jaynius.securitydemo.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.jaynius.securitydemo.entity.AppUser;
import com.jaynius.securitydemo.entity.Product;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude
public class ReqResponse {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String password;
    private String role;
    private AppUser appUsers;
    private List<Product> products;

}
