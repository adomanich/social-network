package com.example.demo.controller;

import com.example.demo.entity.Login;
import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService service;


    @PostMapping("sing-up")
    public String registerNewUser(@RequestBody Login login) {
        return "";
    }
}
