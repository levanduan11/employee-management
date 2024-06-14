package com.coding.user;

import com.coding.domain.model.User;
import net.datafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserFactory {
    private static final Faker faker = new Faker();
    static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static User create() {
        User user = new User();
        user.setUsername(faker.internet().username());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(passwordEncoder.encode(faker.internet().password()));
        return user;
    }


    public static void main(String[] args) {
        User user = new User();
        user.setPassword(faker.internet().password());
        System.out.println(user);
    }
}
