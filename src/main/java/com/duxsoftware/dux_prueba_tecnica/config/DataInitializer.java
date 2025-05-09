package com.duxsoftware.dux_prueba_tecnica.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.duxsoftware.dux_prueba_tecnica.DTO.UsuarioRequest;
import com.duxsoftware.dux_prueba_tecnica.models.Usuario;
import com.duxsoftware.dux_prueba_tecnica.repositories.UsuarioRepository;
import com.duxsoftware.dux_prueba_tecnica.services.UsuarioService;

import jakarta.annotation.PostConstruct;


@Component
public class DataInitializer {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostConstruct
    public void initialize() {
        Optional<Usuario> existingUser = usuarioRepository.findByUsername("test");
        if (existingUser.isEmpty()) {
            UsuarioRequest usuarioRequest = new UsuarioRequest("test", "12345");
            usuarioService.register(usuarioRequest);
            System.out.println("Usuario 'test' creado automáticamente con contraseña '12345'");
        } else {
            System.out.println("El usuario 'test' ya existe en la base de datos.");
        }
    }
}
