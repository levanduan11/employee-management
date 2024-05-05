package com.coding;

import com.coding.repository.UserRepository;
import com.coding.service.MailService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    ApplicationRunner runner(
            JPAQueryFactory queryFactory,
            EntityManager entityManager,
            UserRepository userRepository,
            MailService mailService
    ) {
        return args -> {

        };
    }
}

