package com.app.Cookie.controller;

import com.app.Cookie.model.User;
import com.app.Cookie.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador principal que maneja las rutas web (URLs) para la autenticación,
 * registro y el panel principal (dashboard).
 */
@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Muestra la página de login.
     * Captura parámetros de la URL para mostrar mensajes de error o éxito al salir.
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Usuario o contraseña incorrectos");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "Sesión cerrada correctamente");
        }
        return "login"; // Retorna la vista "login.html"
    }

    /**
     * Muestra la página del formulario de registro.
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Retorna la vista "register.html"
    }

    /**
     * Procesa los datos enviados desde el formulario de registro.
     * Recibe los parámetros, intenta registrar al usuario y redirige según el resultado.
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String fullName,
                               RedirectAttributes redirectAttributes) {
        try {
            userService.register(username, password, fullName);
            // Mensaje flash que se mostrará una sola vez tras la redirección
            redirectAttributes.addFlashAttribute("successMsg", "¡Cuenta creada! Inicia sesión");
            return "redirect:/login"; // Redirige a la página de login
        } catch (RuntimeException e) {
            // Si hay error (ej. usuario ya existe), vuelve al registro con el mensaje
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/register";
        }
    }

    /**
     * Dashboard protegido — solo accesible para usuarios autenticados.
     * Muestra datos de la sesión actual almacenada en la base de datos y vinculada a la cookie.
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, HttpSession session, Model model) {
        // Obtiene el nombre del usuario autenticado actualmente
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        // Guardar datos en la sesión de Spring (HttpSession)
        // Spring Session JDBC se encargará de guardar esto automáticamente en PostgreSQL
        session.setAttribute("username", username);
        session.setAttribute("fullName", user.getFullName());
        session.setAttribute("role", user.getRole());
        session.setAttribute("loginTime", java.time.LocalDateTime.now().toString());

        // Pasar los datos a la vista (Thymeleaf) para mostrarlos en el HTML
        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getId()); // El ID de la sesión (valor de la cookie)
        model.addAttribute("loginTime", session.getAttribute("loginTime"));
        model.addAttribute("cookieName", "COOKIE_SESSION");

        return "dashboard"; // Retorna la vista "dashboard.html"
    }
}
