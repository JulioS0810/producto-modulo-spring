package com.perfumeriavioleta.productomodulo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

/**
 * Utilidad para generar y validar tokens JWT.
 * Se usa en el proceso de autenticación.
 */
@Component
public class JwtUtil {

    // Clave secreta (debe ser larga y segura). En producción usar variable de entorno.
    private static final String SECRET = "MiClaveSuperSecretaParaJWT1234567890!";
    private static final long EXPIRATION = 86400000; // 24 horas en milisegundos

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /**
     * Genera un token JWT a partir del email del usuario.
     * @param email email del usuario autenticado
     * @return token JWT como String
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
     * Valida un token JWT (estructura y firma).
     * @param token token a validar
     * @return true si es válido, false en caso contrario
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
     * Extrae el email (subject) del token JWT.
     * @param token token JWT
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