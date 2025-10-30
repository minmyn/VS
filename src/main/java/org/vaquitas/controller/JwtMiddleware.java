package org.vaquitas.controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.Map;

public class JwtMiddleware {
    private final TokenManager tokenManager;
    public JwtMiddleware(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }


}