package org.vaquitas.controller;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Middleware de seguridad encargado de la validación de tokens JWT (JSON Web Token)
 * para proteger las rutas de la API que requieren autenticación.
 * <p>
 * Verifica la presencia y validez del token en el encabezado {@code Authorization: Bearer <token>}
 * y el ID del usuario en el encabezado {@code User-Id}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class JwtMiddleware {

    private final TokenManager tokenManager;

    /**
     * Constructor que inyecta la dependencia del gestor de tokens.
     *
     * @param tokenManager El gestor de tokens responsable de crear y validar los JWT.
     */
    public JwtMiddleware(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * Aplica el middleware a las rutas específicas que requieren autenticación.
     * <p>
     * Las rutas comentadas indican las áreas que deberían estar protegidas.
     * Se utiliza {@code app.before()} para interceptar la solicitud antes de que llegue al controlador.
     * </p>
     *
     * @param app La instancia de {@link Javalin} de la aplicación.
     */
    public void apply(Javalin app) {
         //Rutas protegidas :D
         app.before("/alimentos*", this::validateJwt);
         app.before("/ganado*", this::validateJwt);
         app.before("/usuarios", this::validateJwt);
         app.before("/usuarios/{id}", this::validateJwt);
         app.before("/receta*", this::validateJwt);
         app.before("/medicamentos*", this::validateJwt);
         app.before("/razas*", this::validateJwt);
         app.before("/ranchos*", this::validateJwt);

    }

    /**
     * Lógica principal de validación del JWT.
     * <p>
     * 1. Verifica el encabezado 'Authorization'.
     * 2. Verifica el encabezado 'User-Id'.
     * 3. Llama a {@code TokenManager} para validar la firma y la caducidad del token contra el ID del usuario.
     * </p>
     *
     * @param ctx El contexto de la petición HTTP de Javalin.
     * @throws UnauthorizedResponse Si la autenticación falla en cualquier paso.
     */
    private void validateJwt(Context ctx) {
        String authHeader = ctx.header("Authorization");
        String userId = ctx.header("User-Id");

        // 1. Verificar Authorization header
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ctx.status(401).json(Map.of(
                    "error", "Authorization header faltante o malformado"
            ));
            System.out.println("Authorization header faltante o malformado");
            throw  new UnauthorizedResponse("Authorization header faltante o malformado");
        }

        // 2. Verificar User-Id header
        if (userId == null) {
            ctx.status(401).json(Map.of(
                    "error", "User-Id header requerido"
            ));
            throw  new UnauthorizedResponse("Authorization  User-Id header requerido");
        }

        String token = authHeader.substring(7); // Extraer el token después de "Bearer "

        // 3. Validar token y User-Id
        try {
            if (!tokenManager.validateToken(token, userId)) {
                ctx.status(403).json(Map.of(
                        "error", "Token inválido o expirado"
                ));
                // 403 Forbidden: La autenticación es válida, pero el permiso para este recurso es denegado (ej. token caducado/inválido).
                throw  new UnauthorizedResponse("Authorization: Token inválido o expirado");
            }
        } catch (Exception e) {
            // Error interno al procesar la validación (ej. problema de firma/clave).
            ctx.status(401).json(Map.of(
                    "error", "Error al validar el token"
            ));
            throw  new UnauthorizedResponse("Error al validar el token");
        }
    }

    /**
     * Manejador de errores para interceptar la excepción {@link UnauthorizedResponse} lanzada
     * por {@code validateJwt} y formatear la respuesta HTTP.
     *
     * @param e La excepción {@link UnauthorizedResponse} capturada.
     * @param ctx El contexto de la petición HTTP de Javalin.
     */
    public void noautorizado(UnauthorizedResponse e, Context ctx){
        // Este método permite personalizar la respuesta JSON final cuando se lanza UnauthorizedResponse
        ctx.status(401).json(Map.of(
                "error", "No autorizado: " + e.getMessage()
        ));
    }

}