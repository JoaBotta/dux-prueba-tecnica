package com.duxsoftware.dux_prueba_tecnica.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duxsoftware.dux_prueba_tecnica.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}