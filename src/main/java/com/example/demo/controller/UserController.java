package com.example.demo.controller;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<UserModel> createNewUser(@RequestBody UserModel userModel) {
        UserModel userModel1 = userService.saveUser(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userModel1);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> userModelList = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(userModelList);
    }
}
