package com.mtrain.mtrain.controller;

import com.mtrain.mtrain.model.User;
import com.mtrain.mtrain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Aquí puedes poner los @GetMapping para las páginas normales (index, entrenamientos, etc.)

    @GetMapping({"/", "/index"})
    public String mostrarIndex() {
        return "index";
    }

    @GetMapping("/sobreNosotros")
    public String sobreNosotros() {
        return "sobreNosotros";
    }

    @GetMapping("/tarifas")
    public String tarifas() {
        return "tarifas";
    }



    @GetMapping("/entrenamientos")
    public String entrenamientos() {
        return "entrenamientos";
    }

    @GetMapping("/entrenamiento_basico")
    public String entrenamiento_basico() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        if (this.userRepository.findByCorreoElectronico(correo).get().getPlan().getId() != 1) {
            return "redirect:/";
        }
        return "entrenamiento_basico";
    }

    @GetMapping("/full_body")
    public String full_body() {
        return "full_body";
    }

    @GetMapping("/cardio")
    public String cardio() {
        return "cardio";
    }

    @GetMapping("/estiramientos")
    public String estiramientos() {
        return "estiramientos";
    }

    @GetMapping("/entrenamiento_intermedio")
    public String entrenamiento_intermedio() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        if (this.userRepository.findByCorreoElectronico(correo).get().getPlan().getId() != 2) {
            return "redirect:/";
        }
        return "entrenamiento_intermedio";
    }

    @GetMapping("/torso_pierna")
    public String torso_pierna() {
        return "torso_pierna";
    }

    @GetMapping("/hiit")
    public String hiit() {
        return "hiit";
    }

    @GetMapping("/core_movilidad")
    public String core_movilidad() {
        return "core_movilidad";
    }

    @GetMapping("/entrenamiento_avanzado")
    public String entrenamiento_avanzado() {
        String correo = SecurityContextHolder.getContext().getAuthentication().getName();
        if (this.userRepository.findByCorreoElectronico(correo).get().getPlan().getId() != 3) {
            return "redirect:/";
        }
        return "entrenamiento_avanzado";
    }

    @GetMapping("/funcionalidad")
    public String funcionalidad() {
        return "funcionalidad";
    }

    @GetMapping("/endurance")
    public String endurance() {
        return "endurance";
    }

    @GetMapping("/fuerza")
    public String fuerza() {
        return "fuerza";
    }

    @GetMapping("/nutricion")
    public String nutricion() {
        return "nutricion";
    }

    @GetMapping("/dieta_definicion")
    public String dieta_definicion() {
        return "dieta_definicion";
    }

    @GetMapping("/dieta_ganar_masa")
    public String dieta_ganar_masa() {
        return "dieta_ganar_masa";
    }

    @GetMapping("/dieta_perder_peso")
    public String dieta_perder_peso() {
        return "dieta_perder_peso";
    }
}
