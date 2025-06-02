package com.mtrain.mtrain.config;

import com.mtrain.mtrain.model.Plan;
import com.mtrain.mtrain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByCorreoElectronico(username)
                .map(user -> org.springframework.security.core.userdetails.User.withUsername(user.getCorreoElectronico())
                        .password(user.getContrasena())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            System.out.println("Login exitoso para: " + authentication.getName());  // Log nombre de usuario

            new HttpSessionRequestCache().removeRequest(request, response);

            String correo = authentication.getName();
            var userOpt = userRepository.findByCorreoElectronico(correo);

            if (userOpt.isPresent()) {
                Plan plan = userOpt.get().getPlan();
                System.out.println("Plan del usuario: " + plan);  // Log plan para debug

                if (plan != null) {
                    switch (plan.getId()) {
                        case 1:
                            System.out.println("Redirigiendo a entrenamiento_basico");
                            response.sendRedirect("/entrenamiento_basico");
                            break;
                        case 2:
                            System.out.println("Redirigiendo a entrenamiento_intermedio");
                            response.sendRedirect("/entrenamiento_intermedio");
                            break;
                        case 3:
                            System.out.println("Redirigiendo a entrenamiento_avanzado");
                            response.sendRedirect("/entrenamiento_avanzado");
                            break;
                    }
                    return;
                }
            } else {
                System.out.println("Usuario no encontrado en la base de datos!");
            }

            System.out.println("Redirigiendo a la página principal '/'");
            response.sendRedirect("/");
        };
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/registro", "/uneteAhora", "/iniciaSesion")
                )
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas accesibles sin autenticación
                        .requestMatchers(
                                "/", "/index", "/registro", "/uneteAhora", "/iniciaSesion",
                                "/sobreNosotros", "/tarifas", "/entrenamientos",
                                "/css/**", "/js/**", "/imagenes/**"
                        ).permitAll()
                        // Cualquier otra solicitud requiere autenticación
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/iniciaSesion")
                        .loginProcessingUrl("/iniciaSesion")
                        .successHandler(myAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/iniciaSesion?logout")
                        .permitAll()
                );

        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);  // Usa el inyectado
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}