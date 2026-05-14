package com.app.Cookie.config;

import com.app.Cookie.model.User;
import com.app.Cookie.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        "Administrador",
                        "ADMIN"
                );
                userRepository.save(admin);
                System.out.println("✔ Usuario admin creado (admin / admin123)");
            }
        };
    }
}
