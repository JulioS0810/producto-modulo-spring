package com.perfumeriavioleta.productomodulo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración de seguridad de Spring Security para la API REST.
 * <p>
 * Se deshabilita CSRF (por ser stateless con JWT), se configura CORS básico,
 * se definen rutas públicas y privadas, y se fuerza el uso de sesiones stateless.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define la cadena de filtros de seguridad.
     * <p>
     * - Desactiva CSRF (no necesario en APIs stateless).
     * - Desactiva CORS por defecto (se puede personalizar más adelante).
     * - Permite acceso sin autenticación a {@code /api/auth/**} y {@code /api/productos/**}.
     * - Cualquier otra ruta requiere autenticación.
     * - La política de sesiones es STATELESS (no se crean cookies JSESSIONID).
     * </p>
     *
     * @param http objeto para construir la configuración HTTP
     * @return el filtro de seguridad configurado
     * @throws Exception en caso de error de configuración
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())               // Deshabilitar CSRF
            .cors(cors -> cors.disable())                // Deshabilitar CORS (o configurarlo si se necesita)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/api/productos/**").permitAll()  // Endpoints públicos
                .anyRequest().authenticated()            // El resto requiere autenticación
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Sin estado

        return http.build();
    }
}