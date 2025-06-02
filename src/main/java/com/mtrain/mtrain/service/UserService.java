package com.mtrain.mtrain.service;

import com.mtrain.mtrain.dto.UserRegisterDTO;
import com.mtrain.mtrain.model.Plan;
import com.mtrain.mtrain.model.User;
import com.mtrain.mtrain.repository.PlanRepository;
import com.mtrain.mtrain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PlanRepository planRepository;

    // Método para cargar usuario por correo electrónico para Spring Security
    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        User user = userRepository.findByCorreoElectronico(correoElectronico)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + correoElectronico));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getCorreoElectronico())
                .password(user.getContrasena())
                .roles("USER") // Puedes agregar más roles si tienes
                .build();
    }

    // Método para registrar nuevo usuario
    public User registerNewUser(UserRegisterDTO dto) throws IllegalArgumentException {
        // Verificar si el correo ya existe
        if (userRepository.existsByCorreoElectronico(dto.getCorreoElectronico())) {
            throw new IllegalArgumentException("Correo ya registrado");
        }

        // Verificar que las contraseñas coincidan
        if (!dto.getContrasena().equals(dto.getConfirmarContrasena())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }

        Plan plan = this.planRepository.findById(dto.getPlanId()).get();

        // Crear la entidad User y asignar valores
        User user = new User();
        user.setNombre(dto.getNombre());
        user.setCiudad(dto.getCiudad());
        user.setCodigoPostal(dto.getCodigoPostal());
        user.setPais(dto.getPais());
        user.setCorreoElectronico(dto.getCorreoElectronico());
        user.setContrasena(passwordEncoder.encode(dto.getContrasena()));
        user.setPlan(plan);
        user.setMetodoPago(dto.getMetodoPago());

        // Guardar en la base de datos
        return userRepository.save(user);
    }
}
