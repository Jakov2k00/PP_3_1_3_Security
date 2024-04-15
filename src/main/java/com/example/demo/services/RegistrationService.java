package com.example.demo.services;

import com.example.demo.models.User;

public interface RegistrationService {
    void registerAdmin(User user);
    void registerUser(User user);
}
