package com.example.demo.services;

import com.example.demo.models.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByFirstName(String firstName);
    void editUser(User user, List<String> roles);
    void deleteUser(Long id);
}
