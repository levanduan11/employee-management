package com.coding.user;

import com.coding.domain.model.Role;
import com.coding.domain.model.User;
import com.coding.repository.RoleRepository;
import com.coding.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testCreateWithRoleAdmin() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setEmail("lvduan1972@gmail.com");
        user.setActivated(true);
        roleRepository.findByName(Role.RoleName.ROLE_ADMIN).ifPresent(
                role -> user.setRoles(Set.of(role))
        );
        userRepository.save(user);
        assertThat(user.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateWithRoleUser() {
        User user = new User();
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setEmail("chokkhacchoiem799@gmail.com");
        user.setActivated(true);
        roleRepository.findByName(Role.RoleName.ROLE_USER).ifPresent(
                role -> user.setRoles(Set.of(role))
        );
        userRepository.save(user);
        assertThat(user.getId()).isGreaterThan(0);
    }
    @Test
    public void testFindAll(){
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(2);
    }
    @Test
    public void testFindByUsername(){
        Optional<User> admin = userRepository.findByUsername("admin");
        assertThat(admin.isPresent()).isEqualTo(true);
    }
}
