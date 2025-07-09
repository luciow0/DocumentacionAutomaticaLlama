package com.martelli.meatmarket.controller;

import com.martelli.meatmarket.Enum.Rol;
import com.martelli.meatmarket.dtos.*;
import com.martelli.meatmarket.model.entidades.Usuario;
import com.martelli.meatmarket.repository.UsuarioRepository;
import com.martelli.meatmarket.services.Security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(JwtUtil jwtUtil, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest request) {

        Rol rol = (request.getRol() == null) ? Rol.CLIENTE : request.getRol();

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(rol)
                .build();

        usuarioRepository.save(nuevoUsuario);

        String token = jwtUtil.generarToken(nuevoUsuario.getEmail(), nuevoUsuario.getRol().name());


        RegisterResponse response = new RegisterResponse(
                token,
                nuevoUsuario.getNombre(),
                nuevoUsuario.getEmail(),
                nuevoUsuario.getRol().name()
        );


        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        Usuario user = userOpt.get();

        if (!user.isActivo()) {
            return ResponseEntity.status(403).body("El usuario está desactivado");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        String token = jwtUtil.generarToken(user.getEmail(), user.getRol().name());

        LoginResponse response = new LoginResponse(
                token,
                user.getEmail(),
                user.getRol().name()
        );

        return ResponseEntity.ok(response);
    }





    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        Usuario actual = (Usuario) request.getAttribute("usuarioActual");

        LoginResponse response = new LoginResponse(
                null, // no reenviás el token
                actual.getEmail(),
                actual.getRol().name()
        );

        return ResponseEntity.ok(response);
    }

}
