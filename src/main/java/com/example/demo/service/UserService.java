package com.example.demo.service;

import com.example.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserModel saveUser(UserModel model) {
        model.setId(System.currentTimeMillis());
        return userRepository.save(model);
    }

    public List<UserModel> findAllUsers() {
        return userRepository.findAll();
    }
}
