package com.coding.user;

import com.coding.domain.model.Employee;
import com.coding.domain.model.Role;
import com.coding.domain.model.User;
import com.coding.employee.EmployeeFactory;
import com.coding.repository.EmployeeRepository;
import com.coding.repository.RoleRepository;
import com.coding.repository.UserRepository;
import com.coding.repository.impl.UserRepositoryImpl;
import com.coding.role.RoleFactory;
import com.coding.web.response.UserProfileResponse;
import com.coding.web.response.UserResponse;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({UserRepositoryImpl.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    private User testAdminUser;
    private User testUserUser;


    @BeforeEach
    void setUp() {
        Role roleAdmin = RoleFactory.createRoleAdmin();
        Role roleUser = RoleFactory.createRoleUser();

        BDDMockito.given(roleRepository.findByName(Role.RoleName.ROLE_ADMIN)).willReturn(Optional.of(roleAdmin));
        BDDMockito.given(roleRepository.findByName(Role.RoleName.ROLE_USER)).willReturn(Optional.of(roleUser));

        Employee employeeAdmin = EmployeeFactory.create();
        employeeAdmin.setFirstName("admin first name");
        employeeAdmin.setLastName("admin last name");
        employeeAdmin.setPhone("admin phone");
        employeeAdmin.setImageUrl("admin image url");
        employeeAdmin.setSalary(BigDecimal.valueOf(1000));

        testAdminUser = UserFactory.create();
        roleRepository.findByName(Role.RoleName.ROLE_ADMIN).ifPresent(r -> testAdminUser.setRoles(new HashSet<>() {{
            add(r);
        }}));
        employeeAdmin.setUser(testAdminUser);
        testAdminUser.setEmployee(employeeAdmin);
        testAdminUser.setUsername("testAdminUser");
        testAdminUser.setEmail("testAdminUser@gmail.com");
        userRepository.save(testAdminUser);

        Employee employeeUser = EmployeeFactory.create();
        employeeAdmin.setFirstName("user first name");
        employeeAdmin.setLastName("user last name");
        employeeAdmin.setPhone("user phone");
        employeeAdmin.setImageUrl("user image url");
        employeeAdmin.setSalary(BigDecimal.valueOf(3000));

        testUserUser = UserFactory.create();
        roleRepository.findByName(Role.RoleName.ROLE_USER).ifPresent(r -> testUserUser.setRoles(new HashSet<>() {{
            add(r);
        }}));
        employeeUser.setUser(testUserUser);
        testUserUser.setEmployee(employeeUser);
        testUserUser.setUsername("testUserUser");
        testUserUser.setEmail("testUserUser@gmail.com");
        userRepository.save(testUserUser);

        List<User> testUsers = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            User user = UserFactory.create();
            if (i % 2 == 0) {
                roleRepository.findByName(Role.RoleName.ROLE_USER).ifPresent(r -> user.setRoles(new HashSet<>() {{
                    add(r);
                }}));
            } else {
                roleRepository.findByName(Role.RoleName.ROLE_ADMIN).ifPresent(r -> user.setRoles(new HashSet<>() {{
                    add(r);
                }}));
            }
            Employee employee = EmployeeFactory.create();
            employee.setFirstName("test first name" + i);
            employee.setLastName("test last name" + i);
            employee.setPhone("test phone" + i);
            employee.setImageUrl("test image url" + i);
            employee.setSalary(BigDecimal.valueOf(1000));

            employee.setUser(user);
            user.setEmployee(employee);
            user.setUsername("testUser" + i);
            user.setEmail("testUser" + i + "@gmail.com");
            testUsers.add(user);
        }

        userRepository.saveAll(testUsers);
    }

    @Test
    void testCreateRoleAdmin() {
        User user = UserFactory.create();
        Role role = RoleFactory.createRoleAdmin();
        BDDMockito.given(roleRepository.findByName(Role.RoleName.ROLE_ADMIN)).willReturn(Optional.of(role));
        roleRepository.findByName(Role.RoleName.ROLE_ADMIN).ifPresent(
                r -> user.setRoles(Set.of(r))
        );
        Employee employee = EmployeeFactory.create();
        employee.setUser(user);
        user.setEmployee(employee);
        userRepository.save(user);

        // Assertion
        assertThat(user.getId()).isGreaterThan(0);
        assertThat(user.getRoles().size()).isEqualTo(1);
        assertThat(user.getRoles().iterator().next().getName()).isEqualTo(Role.RoleName.ROLE_ADMIN);

        assertThat(employee.getId()).isGreaterThan(0);
        assertThat(employee.getUser().getId()).isGreaterThan(0);
        assertThat(employee.getUser().getRoles().size()).isEqualTo(1);
        assertThat(employee.getUser().getRoles().iterator().next().getName()).isEqualTo(Role.RoleName.ROLE_ADMIN);

        assertThat(user.getEmployee().getId()).isGreaterThan(0);
        assertThat(user.getEmployee().getUser().getId()).isGreaterThan(0);
        assertThat(user.getEmployee().getUser().getRoles().size()).isEqualTo(1);
        assertThat(user.getEmployee().getUser().getRoles().iterator().next().getName()).isEqualTo(Role.RoleName.ROLE_ADMIN);
    }

    @Test
    void testCreateRoleUser() {
        User user = UserFactory.create();
        Role role = RoleFactory.createRoleUser();
        BDDMockito.given(roleRepository.findByName(Role.RoleName.ROLE_USER)).willReturn(Optional.of(role));
        roleRepository.findByName(Role.RoleName.ROLE_USER).ifPresent(
                r -> user.setRoles(Set.of(r))
        );
        Employee employee = EmployeeFactory.create();
        employee.setUser(user);
        user.setEmployee(employee);
        userRepository.save(user);

        // Assertion
        assertThat(user.getId()).isGreaterThan(0L);
        assertThat(user.getRoles().size()).isEqualTo(1);
        assertThat(user.getRoles().iterator().next().getName()).isEqualTo(Role.RoleName.ROLE_USER);

        assertThat(employee.getId()).isGreaterThan(0L);
        assertThat(employee.getUser().getId()).isGreaterThan(0L);
        assertThat(employee.getUser().getRoles().size()).isEqualTo(1);
        assertThat(employee.getUser().getRoles().iterator().next().getName()).isEqualTo(Role.RoleName.ROLE_USER);

        assertThat(user.getEmployee().getId()).isGreaterThan(0L);
        assertThat(user.getEmployee().getUser().getId()).isGreaterThan(0L);
        assertThat(user.getEmployee().getUser().getRoles().size()).isEqualTo(1);
        assertThat(user.getEmployee().getUser().getRoles().iterator().next().getName()).isEqualTo(Role.RoleName.ROLE_USER);
    }

    @Test
    void testUpdate() {
        if (testAdminUser != null && testAdminUser.getId() != null) {
            Long id = testAdminUser.getId();
            Optional<User> optionalUser = userRepository.findById(id);

            assertThat(optionalUser).isPresent();
            User fetchedUser = optionalUser.get();

            fetchedUser.setUsername("updatedUsername");
            fetchedUser.setEmail("updatedEmail@gmail.com");

            Employee employee = fetchedUser.getEmployee();
            employee.setFirstName("updated first name");
            employee.setLastName("updated last name");
            employee.setPhone("updated phone");
            employee.setImageUrl("updated image url");
            employee.setSalary(BigDecimal.valueOf(4000));

            userRepository.save(fetchedUser);

            Optional<User> optionalUpdatedUser = userRepository.findById(id);
            assertThat(optionalUpdatedUser).isPresent();
            User updatedUser = optionalUpdatedUser.get();
            assertThat(updatedUser.getUsername()).isEqualTo("updatedUsername");
            assertThat(updatedUser.getEmail()).isEqualTo("updatedEmail@gmail.com");

            assertThat(updatedUser.getEmployee().getFirstName()).isEqualTo("updated first name");
            assertThat(updatedUser.getEmployee().getLastName()).isEqualTo("updated last name");
            assertThat(updatedUser.getEmployee().getPhone()).isEqualTo("updated phone");
            assertThat(updatedUser.getEmployee().getImageUrl()).isEqualTo("updated image url");
            assertThat(updatedUser.getEmployee().getSalary()).isEqualTo(BigDecimal.valueOf(4000));
        }
    }

    @Test
    void testDeleteById() {
        if (testAdminUser != null && testAdminUser.getId() != null) {
            Long id = testAdminUser.getId();
            userRepository.deleteById(id);
            Optional<User> optionalUser = userRepository.findById(id);
            assertThat(optionalUser).isNotPresent();
        }
    }

    @Test
    void testFindAll() {
        Predicate predicateTrue = Expressions.TRUE;
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<UserResponse> page = userRepository.findAllWithEmployee(predicateTrue, pageRequest);

        List<UserResponse> content = page.getContent();

        // Assertion
        assertThat(page.getTotalElements()).isGreaterThan(0);
        assertThat(content).isNotEmpty();
    }

    @Test
    void testFindByUsernameFound() {
        String username = "testAdminUser";
        Optional<UserProfileResponse> userProfileResponse = userRepository.findOneByUsername(username);

        // Assertion
        assertThat(userProfileResponse).isPresent();
    }

    @Test
    void testFindByUsernameNotFound() {
        String username = "incorrect_username";
        Optional<UserProfileResponse> oneByUsername = userRepository.findOneByUsername(username);

        // Assertion
        assertThat(oneByUsername).isNotPresent();
    }

    @Test
    void testFindByEmailFound() {
        String email = "testAdminUser@gmail.com";
        Optional<User> userProfileResponse = userRepository.findByEmail(email);

        // Assertion
        assertThat(userProfileResponse).isPresent();
    }

    @Test
    void testFindByEmailNotFound(){
        String email = "incorrect@gmail.com";
        Optional<User> byEmail = userRepository.findByEmail(email);

        // Assertion
        assertThat(byEmail).isNotPresent();
    }
}
