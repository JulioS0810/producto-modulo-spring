package com.perfumeriavioleta.productomodulo.repository;

import com.perfumeriavioleta.productomodulo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link Usuario}.
 * <p>
 * Además de los métodos CRUD, proporciona consultas específicas
 * para autenticación y validación de email.
 * </p>
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su email (único en la base de datos).
     *
     * @param email correo electrónico del usuario
     * @return Optional con el usuario si existe, vacío si no
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica si ya existe un usuario con el email dado.
     *
     * @param email email a comprobar
     * @return true si ya está registrado, false en caso contrario
     */
    boolean existsByEmail(String email);
}