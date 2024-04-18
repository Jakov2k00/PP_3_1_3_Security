package com.example.demo.services;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.until.RoleCryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(@Lazy UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
        Optional<User> userOpt = userRepository.findByFirstName(firstName);

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

        Set<Role> currentRoles = user.getRoles();

        Set<Long> newRoleIds = roles.stream()
                .map(Long::valueOf)
                .collect(Collectors.toSet());

        Set<Long> newRolesToAdd = newRoleIds.stream()
                .filter(roleId -> currentRoles.stream().noneMatch(role -> role.getId().equals(roleId)))
                .collect(Collectors.toSet());

        Set<Role> newRoles = newRolesToAdd.stream()
                .map(roleRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(role -> {
                    String encryptedRoleName = RoleCryptor.encryptRole(role.getRoleName());
                    Role encryptedRole = new Role();
                    encryptedRole.setRoleName(encryptedRoleName);
                    return encryptedRole;
                })
                .collect(Collectors.toSet());

        currentRoles.addAll(newRoles);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(userRepository.getById(id));
    }

    private void setUserRoles(User user, String[] rolesNames) {
        Set<Role> rolesSet = new HashSet<>();
        for (String roleName : rolesNames) {
            Role role = roleService.findRoleByRoleName(RoleCryptor.encryptRole(roleName));
            if (role == null) {
                role = new Role(RoleCryptor.encryptRole(roleName));
                roleService.saveRole(role);
            }
            rolesSet.add(role);
        }
        user.setRoles(rolesSet);
    }

    @Override
    public void registerAdmin(User user) {
        String[] roles = new String[]{RoleCryptor.getAdminRoleName()};
        setUserRoles(user, roles);

        userRepository.save(user);
    }

    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String[] roles = new String[]{RoleCryptor.getUserRoleName()};
        setUserRoles(user, roles);

        userRepository.save(user);
    }
}
