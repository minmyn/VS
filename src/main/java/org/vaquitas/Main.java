package org.vaquitas;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.javalin.Javalin;
import org.vaquitas.config.Inicio;
import org.vaquitas.controller.JwtMiddleware;
import org.vaquitas.controller.TokenManager;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8548);
        System.out.println("Corriendo en el puerto 8548");
        app.get("/", ctx  -> ctx.result("Hello Vaquita"));
        /*app.exception(JsonParseException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.json(Map.of("error", "JSON mal formado", "message", "La petición no tiene sintaxis JSON válida."));
        });
        app.exception(JsonMappingException.class, (e, ctx) -> {
            ctx.status(400);
            ctx.json(Map.of("error", "JSON mal formado", "message", "La petición no tiene sintaxis JSON válida."));
        });*/
        TokenManager tokenManager = new TokenManager();
        JwtMiddleware jwtMiddleware = new JwtMiddleware(tokenManager);

        jwtMiddleware.apply(app);
        Inicio.inicioUsuario().register(app);
        Inicio.inicioGanado().register(app);
        Inicio.inicioVenta().register(app);
        Inicio.inicioRaza().register(app);
        Inicio.inicioRancho().register(app);
        Inicio.inicioAlimento().register(app);
        Inicio.inicioMedicina().register(app);
        Inicio.inicioConsulta().register(app);
        Inicio.inicioReceta().register(app);
        Inicio.inicioRecordatorio().register(app);

    }
}