package org.vaquitas.controller;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;

public class TokenManager {
    private final SecretKey key;
    private static final long EXPIRATION_TIME = 360000000; // 1 hora

    public TokenManager() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

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