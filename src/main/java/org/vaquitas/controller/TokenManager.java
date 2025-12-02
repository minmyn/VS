package org.vaquitas.controller;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Clase encargada de la gestión de JSON Web Tokens (JWT).
 * <p>
 * Implementa la lógica para emitir (crear) y validar tokens
 * utilizando una clave secreta HMAC y definiendo un tiempo de expiración.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class TokenManager {
    /** Clave secreta estática utilizada para firmar y verificar los tokens. Debe ser robusta y almacenada de forma segura. */
    private static final String SECRET = "UnaClaveSuperSecretaQueSeaLarga1234567890";

    /** Objeto {@link SecretKey} generado a partir del {@code SECRET} para el algoritmo HMAC. */
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    /** * Tiempo de expiración del token en milisegundos.
     * El valor de 3,600,000 ms equivale a exactamente una (1) hora.
     */
    private static final long EXPIRATION_TIME = 3600000; // 1 hora

    /**
     * Constructor por defecto.
     */
    public TokenManager() {}

    /**
     * Emite un nuevo JWT para un usuario.
     * <p>
     * El token incluye:
     * <ul>
     * <li>**Subject (Sub):** El ID del usuario.</li>
     * <li>**Issued At (Iat):** La fecha de creación.</li>
     * <li>**Expiration (Exp):** La fecha de caducidad (1 hora a partir de ahora).</li>
     * </ul>
     * </p>
     *
     * @param userId El identificador único del usuario para incluir en el campo 'subject'.
     * @return Una cadena que representa el JWT firmado y compactado.
     */
    public String issueToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * Valida la autenticidad y caducidad de un token JWT, además de verificar
     * que el token pertenezca al usuario esperado.
     *
     * @param token El token JWT recibido en la petición.
     * @param userId El ID del usuario que afirma poseer el token.
     * @return {@code true} si el token es válido, no ha expirado y el subject coincide con el {@code userId}; {@code false} en caso contrario.
     */
    public boolean validateToken(String token, String userId) {
        try {
            // Parsear, verificar la firma y validar la caducidad del token.
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Verificar si el 'subject' (ID de usuario) coincide.
            return claims.getSubject().equals(userId);

        } catch (JwtException | IllegalArgumentException e) {
            // Captura cualquier error de JWT (firma, expiración, malformato)
            return false;
        }
    }
}