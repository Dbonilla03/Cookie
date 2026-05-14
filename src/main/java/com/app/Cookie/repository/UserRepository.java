package com.app.Cookie.repository;

import com.app.Cookie.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio de la entidad User.
 * Al extender JpaRepository, Spring Data provee automáticamente todos los métodos
 * básicos para la base de datos (guardar, buscar, eliminar, listar, etc.).
 */
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Método personalizado para buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
    
    // Método personalizado para comprobar si un nombre de usuario ya existe
    boolean existsByUsername(String username);
}
