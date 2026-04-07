package com.perfumeriavioleta.productomodulo.controller;

import com.perfumeriavioleta.productomodulo.model.Usuario;
import com.perfumeriavioleta.productomodulo.service.AuthService;
import com.perfumeriavioleta.productomodulo.util.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controlador REST para los servicios de autenticación.
 * Expone endpoints de registro y login.
 * 
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-04-06
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (útil para frontend)
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * 
     * @param usuario Objeto Usuario con nombre, email y password (válido)
     * @return ResponseEntity con mensaje de éxito o error
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario) {
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
            "usuario", Map.of("id", nuevo.getId(), "nombre", nuevo.getNombre(), "email", nuevo.getEmail())
        ));
    }

    /**
     * Autentica a un usuario y devuelve un token JWT.
     * 
     * @param credenciales Mapa con email y password
     * @return ResponseEntity con mensaje "autenticación satisfactoria" o "error en la autenticación"
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
                "mensaje", "autenticación satisfactoria",
                "token", token,
                "usuario", Map.of("id", usuarioOpt.get().getId(), "nombre", usuarioOpt.get().getNombre(), "email", usuarioOpt.get().getEmail())
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("success", false, "mensaje", "error en la autenticación")
            );
        }
    }

    /**
     * Maneja las excepciones de validación (cuando falla @Valid).
     * 
     * @param ex Excepción lanzada por validación
     * @return ResponseEntity con mensaje de error detallado
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(Map.of(
            "success", false,
            "mensaje", "Error de validación: " + errors
        ));
    }
}