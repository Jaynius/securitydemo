package com.jaynius.securitydemo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jaynius.securitydemo.DTO.ReqResponse;
import com.jaynius.securitydemo.entity.Product;
import com.jaynius.securitydemo.repository.ProductsRepository;

@RestController
public class AdminUsers {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/public/product")
    public ResponseEntity<Object> getAllProducts(){
        return ResponseEntity.ok(productsRepository.findAll());
    }

    @PostMapping("/admin/save")
    public ResponseEntity<Object> saveProducts(@RequestBody ReqResponse productResponse){
        Product productTosave= new Product();
        productTosave.setName(productResponse.getName());
        return ResponseEntity.ok(productsRepository.save(productTosave));
    }

    @GetMapping("/users/alone")
    public ResponseEntity<Object> usersAlone(){
        return ResponseEntity.ok("only users can see this");
    }

    @GetMapping("/adminuser/both")
    public ResponseEntity<Object> adminUsers(){
        return ResponseEntity.ok("both admin and users can see this");
    }

    @GetMapping("/public/email")
    public String getCurrentUserEmail(){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getName());
        return authentication.getName();

    }

}
