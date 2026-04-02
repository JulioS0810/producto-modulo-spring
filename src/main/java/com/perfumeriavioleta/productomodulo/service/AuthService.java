package com.perfumeriavioleta.productomodulo.service;

import com.perfumeriavioleta.productomodulo.model.Usuario;
import com.perfumeriavioleta.productomodulo.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de registro y autenticación de usuarios.
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un nuevo usuario en la base de datos.
     * La contraseña se encripta antes de guardar.
     * @param usuario objeto con los datos del usuario (nombre, email, password)
     * @return el usuario guardado (con ID asignado)
     */
    public Usuario registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica a un usuario verificando email y contraseña.
     * @param email email del usuario
     * @param passwordRaw contraseña en texto plano
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
     * @param email email a verificar
     * @return true si ya existe, false si está disponible
     */
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}