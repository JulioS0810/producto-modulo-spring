package com.perfumeriavioleta.productomodulo.controller;

import com.perfumeriavioleta.productomodulo.model.Usuario;
import com.perfumeriavioleta.productomodulo.service.AuthService;
import com.perfumeriavioleta.productomodulo.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

/**
 * Controlador REST para los servicios de autenticación (registro y login).
 * Endpoints: /api/auth/register y /api/auth/login
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite peticiones desde el frontend React
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint de registro de usuario.
     * Espera un JSON con nombre, email y password.
     * @param usuario objeto Usuario recibido en el cuerpo de la petición
     * @return ResponseEntity con mensaje de éxito o error
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        // Validar si el email ya existe
        if (authService.existeEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "mensaje", "El email ya está registrado")
            );
        }

        Usuario nuevo = authService.registrar(usuario);
        String token = jwtUtil.generateToken(nuevo.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "success", true,
            "mensaje", "Usuario registrado exitosamente",
            "token", token,
            "usuario", Map.of(
                "id", nuevo.getId(),
                "nombre", nuevo.getNombre(),
                "email", nuevo.getEmail()
            )
        ));
    }

    /**
     * Endpoint de inicio de sesión.
     * Espera un JSON con email y password.
     * @param credenciales mapa con email y password
     * @return ResponseEntity con mensaje "autenticación satisfactoria" si es correcto,
     *         o "error en la autenticación" si falla.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String password = credenciales.get("password");

        Optional<Usuario> usuarioOpt = authService.autenticar(email, password);
        if (usuarioOpt.isPresent()) {
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "mensaje", "autenticación satisfactoria",  // Texto exacto requerido
                "token", token,
                "usuario", Map.of(
                    "id", usuarioOpt.get().getId(),
                    "nombre", usuarioOpt.get().getNombre(),
                    "email", usuarioOpt.get().getEmail()
                )
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "mensaje", "error en la autenticación"   // Texto exacto requerido
            ));
        }
    }
}