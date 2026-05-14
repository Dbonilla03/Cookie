package com.app.Cookie.config;

// Importaciones necesarias para los modelos y repositorios
import com.app.Cookie.model.User;
import com.app.Cookie.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración encargada de inicializar datos en la base de datos
 * al momento de arrancar la aplicación.
 */
@Configuration
public class DataInitializer {

    /**
     * Este Bean se ejecuta automáticamente al iniciar Spring Boot.
     * Su función es crear un usuario administrador por defecto si no existe ninguno.
     */
    @Bean
    CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Verifica si ya existe un usuario llamado "admin"
            if (!userRepository.existsByUsername("admin")) {
                // Crea el usuario administrador con su contraseña encriptada
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        "Administrador",
                        "ADMIN"
                );
                // Guarda el usuario en la base de datos
                userRepository.save(admin);
                System.out.println("✔ Usuario admin creado (admin / admin123)");
            }
        };
    }
}
