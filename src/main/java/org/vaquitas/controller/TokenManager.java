package org.vaquitas.controller;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class TokenManager {
    private static final String SECRET = "UnaClaveSuperSecretaQueSeaLarga1234567890";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());
//    private final SecretKey key;
    private static final long EXPIRATION_TIME = 360000000; // 1 hora

    public TokenManager() {}

    public String issueToken(String userId) {
        return Jwts.builder().setSubject(userId).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).signWith(key).compact();
    }

    public boolean validateToken(String token, String userId) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject().equals(userId);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}