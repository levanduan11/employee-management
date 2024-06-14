package com.coding.role;

import com.coding.domain.model.Role;

public class RoleFactory {
    public static Role createRoleAdmin() {
        Role role = new Role();
        role.setName(Role.RoleName.ROLE_ADMIN);
        role.setId(1L);
        return role;
    }

    public static Role createRoleUser() {
        Role role = new Role();
        role.setName(Role.RoleName.ROLE_USER);
        role.setId(2L);
        return role;
    }
}
