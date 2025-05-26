package com.duxsoftware.dux_prueba_tecnica.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duxsoftware.dux_prueba_tecnica.DTO.TokenResponse;
import com.duxsoftware.dux_prueba_tecnica.DTO.UsuarioRequest;
import com.duxsoftware.dux_prueba_tecnica.models.Usuario;
import com.duxsoftware.dux_prueba_tecnica.services.UsuarioService;



@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody UsuarioRequest usuarioRequest) {
        String token = usuarioService.register(usuarioRequest);
        TokenResponse response = new TokenResponse(token);
        return ResponseEntity.ok(new TokenResponse(token));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequest usuarioRequest){
        try {
            String token = usuarioService.login(usuarioRequest);
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> getAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

}
