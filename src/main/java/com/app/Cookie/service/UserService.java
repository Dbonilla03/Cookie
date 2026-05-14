package com.app.Cookie.service;

import com.app.Cookie.model.User;
import com.app.Cookie.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio encargado de la lógica de negocio relacionada con los usuarios.
 * Implementa UserDetailsService para integrarse con el proceso de autenticación de Spring Security.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método central de UserDetailsService.
     * Spring Security lo llama automáticamente cuando alguien intenta iniciar sesión.
     * Carga el usuario desde la base de datos y lo convierte al formato que entiende Spring Security.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca al usuario en la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // Retorna un objeto "UserDetails" que Spring Security usará para validar la contraseña
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }

    /**
     * Lógica para registrar un nuevo usuario en el sistema.
     * Encripta la contraseña antes de guardarla.
     */
    public User register(String username, String password, String fullName) {
        // Validación: Verifica si el nombre de usuario ya está ocupado
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        // Crea la entidad User, encriptando la contraseña introducida por el usuario
        User user = new User(
                username,
                passwordEncoder.encode(password),
                fullName,
                "USER"
        );
        
        // Guarda el nuevo usuario en la base de datos
        return userRepository.save(user);
    }

    /**
     * Método auxiliar para obtener el perfil completo de un usuario usando su username.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
