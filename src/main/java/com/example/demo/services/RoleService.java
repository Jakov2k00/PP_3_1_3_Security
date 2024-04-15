package com.example.demo.services;

import com.example.demo.models.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();

    Role findRoleByStringId(String sid);

    Role findRoleByRoleId(Long id);

    Role findRoleByRoleName(String name);

    void saveRole(Role role);
}
