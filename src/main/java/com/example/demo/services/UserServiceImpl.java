package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> loadUserByFirstName(String firstName) throws UsernameNotFoundException {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public Set<Role> getUserRoles(String firstName) {
        Optional<User> userOptional = userRepository.findByFirstName(firstName);
        return userOptional.map(User::getRoles).orElse(Collections.emptySet());
    }
}
