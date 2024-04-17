package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> loadUserByFirstName(String firstName);
    Set<Role> getUserRoles(String firstName);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByFirstName(String firstName);
    void editUser(User user, List<String> roles);
    void deleteUser(Long id);
    void registerAdmin(User user);
    void registerUser(User user);
}