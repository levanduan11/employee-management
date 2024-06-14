package com.coding.role;

import com.coding.domain.model.Role;
import com.coding.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository roleRepository;
    @Test
    public void testFindAll(){
        List<Role> roles = roleRepository.findAll();
        assertThat(roles.size()).isEqualTo(2);
    }

    @Test
    public void testFindByName(){
        Optional<Role> optionalRole = roleRepository.findByName(Role.RoleName.ROLE_ADMIN);

        assertThat(optionalRole.isPresent()).isTrue();
    }

}
