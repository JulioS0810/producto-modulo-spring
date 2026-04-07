package com.perfumeriavioleta.productomodulo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/**
 * Utilidad para la generación, validación y extracción de información de tokens JWT.
 * <p>
 * Se usa en el proceso de autenticación: tras un login exitoso se genera un token
 * que el cliente debe incluir en el encabezado {@code Authorization} para acceder
 * a endpoints protegidos.
 * </p>
 */
@Component
public class JwtUtil {

    // Clave secreta para firmar los tokens. En producción debe obtenerse de una variable de entorno.
    private static final String SECRET = "MiClaveSuperSecretaParaJWT1234567890!";
    private static final long EXPIRATION = 86400000; // 24 horas en milisegundos

    /**
     * Obtiene la clave de firma a partir del secreto en formato String.
     *
     * @return objeto {@link Key} usable para HMAC-SHA256
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * Genera un token JWT para el email del usuario autenticado.
     * <p>
     * El token incluye como subject el email, fecha de emisión y fecha de expiración.
     * </p>
     *
     * @param email email del usuario
     * @return token JWT en formato String
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida la integridad y firma de un token JWT.
     *
     * @param token token a verificar
     * @return {@code true} si el token es válido (estructura y firma correctas),
     *         {@code false} en caso contrario (token expirado, mal formado, firma inválida)
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Extrae el email (subject) contenido en el token JWT.
     *
     * @param token token JWT válido
     * @return email del usuario
     */
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}