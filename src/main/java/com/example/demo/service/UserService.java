package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User model) {
        model.setId(System.currentTimeMillis());
        return userRepository.save(model);
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserIdByEmail(email);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
