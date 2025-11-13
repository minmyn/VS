package org.vaquitas.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
public class JwtMiddleware {

    private final TokenManager tokenManager;

    public JwtMiddleware(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public void apply(Javalin app) {
//        app.before("/usuarios", this::validateJwt);
//        app.before("/usuarios/{id}", this::validateJwt);
//        app.before("/usuarios/{id}", this::validateJwt);
//        app.before("/usuarios/{id}", this::validateJwt);
//        app.before("/usuarios/{id}", this::validateJwt);
    }

    private void validateJwt(Context ctx) {
        String authHeader = ctx.header("Authorization");
        String userId = ctx.header("User-Id");

        // Verificar que existan los headers
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).json(Map.of(
                    "error", "Authorization header faltante o malformado"
            ));
            System.out.println("Authorization header faltante o malformado");
            throw  new UnauthorizedResponse("Authorization header faltante o malformado");
        }

        if (userId == null) {
            ctx.status(401).json(Map.of(
                    "error", "User-Id header requerido"
            ));
            throw  new UnauthorizedResponse("Authorization  User-Id header requerido");
        }

        // Extraer el token
        String token = authHeader.substring(7);

        // Validar el token
        try {
            if (!tokenManager.validateToken(token, userId)) {
                ctx.status(403).json(Map.of(
                        "error", "Token inválido o expirado"
                ));
                throw  new UnauthorizedResponse("Authorization: Token inválido o expirado");
            }
            // Token válido - la solicitud continúa
        } catch (Exception e) {
            ctx.status(401).json(Map.of(
                    "error", "Error al validar el token"
            ));
            throw  new UnauthorizedResponse("Error al validar el token");
        }
    }

    public void noautorizado(UnauthorizedResponse e, Context ctx){
        ctx.status(401).json(Map.of(
                "error", "No autorizado" + e.getMessage()
        ));
    }

}