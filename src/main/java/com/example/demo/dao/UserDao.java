package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void deleteUser(Long id);
    void editUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
}