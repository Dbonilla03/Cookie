package com.app.Cookie.config;

// Importaciones de seguridad de Spring
import com.app.Cookie.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase principal de configuración de seguridad de la aplicación.
 * Aquí se definen las reglas de acceso, el método de encriptación y el manejo de sesiones.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define el algoritmo de encriptación para las contraseñas.
     * Se usa BCrypt, que es el estándar recomendado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura el proveedor de autenticación.
     * Le indica a Spring Security que use nuestro UserService para buscar usuarios
     * y nuestro passwordEncoder para verificar las contraseñas.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Expone el AuthenticationManager, necesario para manejar el proceso de inicio de sesión.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configura la cadena de filtros de seguridad HTTP (Security Filter Chain).
     * Aquí se definen las rutas públicas, privadas y la configuración del login/logout.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. Configuración de autorización de rutas
            .authorizeHttpRequests(auth -> auth
                // Permite acceso libre a login, registro y archivos estáticos (CSS, JS, imágenes)
                .requestMatchers("/login", "/register", "/css/**", "/js/**", "/images/**").permitAll()
                // Cualquier otra petición requerirá que el usuario esté autenticado
                .anyRequest().authenticated()
            )
            // 2. Configuración del formulario de inicio de sesión
            .formLogin(form -> form
                .loginPage("/login") // Ruta de la página de login personalizada
                .defaultSuccessUrl("/dashboard", true) // A dónde ir tras un login exitoso
                .failureUrl("/login?error=true") // A dónde ir si falla el login
                .permitAll()
            )
            // 3. Configuración del cierre de sesión (Logout)
            .logout(logout -> logout
                .logoutUrl("/logout") // Ruta para cerrar sesión
                .logoutSuccessUrl("/login?logout=true") // A dónde ir tras cerrar sesión
                .invalidateHttpSession(true) // Invalida la sesión actual
                .deleteCookies("COOKIE_SESSION") // Elimina la cookie de sesión del navegador
                .permitAll()
            )
            // 4. Configuración del manejo de sesiones
            .sessionManagement(session -> session
                .maximumSessions(1) // Permite solo 1 sesión activa por usuario
            );

        return http.build();
    }
}
