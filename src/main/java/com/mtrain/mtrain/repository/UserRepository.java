package com.mtrain.mtrain.repository;

import com.mtrain.mtrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Método para buscar un usuario por correo electrónico
    Optional<User> findByCorreoElectronico(String correoElectronico);

    boolean existsByCorreoElectronico(String correoElectronico);

}
