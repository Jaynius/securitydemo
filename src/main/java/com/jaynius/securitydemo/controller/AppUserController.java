package com.jaynius.securitydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AppUserController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello we mzee";
    }

}
