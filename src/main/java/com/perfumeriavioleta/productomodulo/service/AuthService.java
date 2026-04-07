package com.perfumeriavioleta.productomodulo.service;

import com.perfumeriavioleta.productomodulo.model.Usuario;
import com.perfumeriavioleta.productomodulo.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de registro y autenticación de usuarios.
 * 
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-04-06
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un nuevo usuario. La contraseña se encripta antes de guardar.
     * 
     * @param usuario Objeto Usuario (sin encriptar)
     * @return Usuario guardado (con ID asignado)
     */
    public Usuario registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica a un usuario verificando email y contraseña.
     * 
     * @param email Email del usuario
     * @param passwordRaw Contraseña en texto plano
     * @return Optional con el usuario si las credenciales son correctas, vacío si no
     */
    public Optional<Usuario> autenticar(String email, String passwordRaw) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent() && passwordEncoder.matches(passwordRaw, usuarioOpt.get().getPassword())) {
            return usuarioOpt;
        }
        return Optional.empty();
    }

    /**
     * Verifica si un email ya está registrado.
     * 
     * @param email Email a verificar
     * @return true si existe, false si está disponible
     */
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}