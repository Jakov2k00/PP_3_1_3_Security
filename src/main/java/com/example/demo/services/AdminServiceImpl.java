package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return userOpt.get();
    }

    @Override
    public User getUserByFirstName(String firstName) {
        Optional<User> userOpt = userRepository.findUserByFirstName(firstName);

        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return userOpt.get();
    }

    @Override
    @Transactional
    public void editUser(User user, List<String> roles) {
        User undoEdit = userRepository.getById(user.getId());
        user.setPassword(undoEdit.getPassword());
        Set<Role> roleSet = roles.stream()
                .map(Long::valueOf)
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(userRepository.getById(id));
    }
}
