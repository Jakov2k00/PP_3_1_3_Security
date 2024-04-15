package com.example.demo.until;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.services.AdminService;
import com.example.demo.services.RegistrationService;
import com.example.demo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MyDataInitializer {

    private final AdminService adminService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    private final RegistrationService registrationService;

    @Autowired
    public MyDataInitializer(AdminService adminService, RoleService roleService, PasswordEncoder passwordEncoder, RegistrationService registrationService) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.registrationService = registrationService;
    }

    @PostConstruct
    public void initializeData() {
        if (adminService.getAllUsers().isEmpty()) {
            Role adminRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");
            roleService.saveRole(adminRole);
            roleService.saveRole(userRole);
            User adminUser = new User();
            adminUser.setFirstName("admin");
            adminUser.setLastName("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            registrationService.registerAdmin(adminUser);
            System.out.println("Users created:");
            System.out.println("Admin: username=admin, password=admin");
        }
    }
}