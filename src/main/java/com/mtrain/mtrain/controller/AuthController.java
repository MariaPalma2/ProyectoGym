package com.mtrain.mtrain.controller;

import com.mtrain.mtrain.dto.UserLoginDTO;
import com.mtrain.mtrain.dto.UserRegisterDTO;
import com.mtrain.mtrain.model.User;
import com.mtrain.mtrain.repository.PlanRepository;
import com.mtrain.mtrain.repository.UserRepository;
import com.mtrain.mtrain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar formulario de registro
    @GetMapping("/uneteAhora")
    public String showRegistrationForm(Model model) {
        model.addAttribute("planes", this.planRepository.findAll());
        return "uneteAhora"; // Vista HTML para registro
    }

    // Procesar registro de usuario
    @PostMapping("/uneteAhora")
    public String registerUser(@ModelAttribute("user") UserRegisterDTO userDto,
                               BindingResult result,
                               Model model) {
        try {
            userService.registerNewUser(userDto);
        } catch (IllegalArgumentException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "uneteAhora";
        }
        // Redirigir a login con parámetro para mostrar mensaje "registrado"
        return "redirect:/iniciaSesion?registrado=true";
    }

    // Mostrar formulario de login
    @GetMapping("/iniciaSesion")
    public String showLoginForm() {
        return "iniciaSesion"; // Vista HTML para login
    }
}
