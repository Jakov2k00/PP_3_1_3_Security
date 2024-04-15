package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> loadUserByFirstName(String firstName);
    Set<Role> getUserRoles(String firstName);
}