package com.perfumeriavioleta.productomodulo.service;

import com.perfumeriavioleta.productomodulo.model.Usuario;
import com.perfumeriavioleta.productomodulo.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
 * Servicio encargado de la lógica de registro y autenticación de usuarios.
 * <p>
 * Utiliza BCrypt para el hash de contraseñas y delega la persistencia en
 * {@link UsuarioRepository}.
 * </p>
 */
@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * <p>
     * La contraseña en texto plano se encripta con BCrypt antes de guardarla.
     * </p>
     *
     * @param usuario objeto con los datos del usuario (sin ID)
     * @return el usuario guardado con su ID generado
     */
    public Usuario registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica a un usuario comprobando email y contraseña.
     *
     * @param email      email del usuario
     * @param passwordRaw contraseña en texto plano proporcionada en el login
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
     * @param email email a consultar
     * @return true si ya existe, false si está disponible
     */
    public boolean existeEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}