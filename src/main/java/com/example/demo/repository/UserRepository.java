package com.example.demo.repository;

import com.example.demo.entity.User;

import java.util.List;

public interface UserRepository {

    boolean saveUser(User model);

    List<User> getAllUsers();

    User getUserById(Long id);

    boolean deleteUser(Long id);

    boolean updateUser(Long id, User user);
}
