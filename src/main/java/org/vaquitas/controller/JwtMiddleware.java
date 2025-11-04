package org.vaquitas.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;
public class JwtMiddleware {

    private final TokenManager tokenManager;

    public JwtMiddleware(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void apply(Javalin app) {
        app.before("/usuarios", this::validateJwt);
    }

    private void validateJwt(Context ctx) {
        String authHeader = ctx.header("Authorization");
        String userId = ctx.header("User-Id");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).json(Map.of(
                    "error", "Authorization header faltante o malformado"
            ));
            return;
        }

        if (userId == null) {
            ctx.status(401).json(Map.of(
                    "error", "User-Id header requerido"
            ));
            return;
        }
        String token = authHeader.substring(7);
        try {
            if (!tokenManager.validateToken(token, userId)) {
                ctx.status(403).json(Map.of(
                        "error", "Token inválido o expirado"
                ));
            }
        } catch (Exception e) {
            ctx.status(401).json(Map.of(
                    "error", "Error al validar el token"
            ));
        }
    }
}