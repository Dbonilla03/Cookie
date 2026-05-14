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

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Muestra la página de login.
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
        return "login";
    }

    /**
     * Muestra la página de registro.
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /**
     * Procesa el registro de un nuevo usuario.
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String fullName,
                               RedirectAttributes redirectAttributes) {
        try {
            userService.register(username, password, fullName);
            redirectAttributes.addFlashAttribute("successMsg", "¡Cuenta creada! Inicia sesión");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
            return "redirect:/register";
        }
    }

    /**
     * Dashboard protegido — muestra datos de sesión capturados en la cookie.
     */
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, HttpSession session, Model model) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        // Guardar datos en la sesión (se persisten en la cookie JDBC)
        session.setAttribute("username", username);
        session.setAttribute("fullName", user.getFullName());
        session.setAttribute("role", user.getRole());
        session.setAttribute("loginTime", java.time.LocalDateTime.now().toString());

        model.addAttribute("user", user);
        model.addAttribute("sessionId", session.getId());
        model.addAttribute("loginTime", session.getAttribute("loginTime"));
        model.addAttribute("cookieName", "COOKIE_SESSION");

        return "dashboard";
    }
}
