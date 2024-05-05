package com.coding.role;

import com.coding.domain.model.Role;
import com.coding.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateWithAdmin() {
        Role role = new Role();
        role.setName(Role.RoleName.ROLE_ADMIN);
        roleRepository.save(role);
        assertThat(role.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateWithUser() {
        Role role = new Role();
        role.setName(Role.RoleName.ROLE_USER);
        roleRepository.save(role);
        assertThat(role.getId()).isGreaterThan(0);
    }
    @Test
    public void testFindAll(){
        List<Role> roles = roleRepository.findAll();
        assertThat(roles.size()).isEqualTo(2);
    }
}
