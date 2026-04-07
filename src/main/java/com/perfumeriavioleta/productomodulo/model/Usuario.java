package com.perfumeriavioleta.productomodulo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * Entidad que representa la tabla {@code usuarios} en la base de datos.
 * <p>
 * Almacena la información de los usuarios registrados para autenticación.
 * La contraseña se guarda encriptada (BCrypt) y el email es único.
 * </p>
 *
 * @author Julio César Suárez Garavito
 * @version 1.0
 * @since 2026-04-02
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El email no puede estar vacío")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // Constructor vacío (requerido por JPA)

    public Usuario() {}

    /**
     * Constructor útil para crear un usuario sin ID (se genera automáticamente).
     *
     * @param nombre   nombre completo del usuario
     * @param email    correo electrónico (único)
     * @param password contraseña en texto plano (se encriptará antes de guardar)
     */

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}