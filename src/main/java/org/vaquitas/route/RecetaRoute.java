package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RecetaControl;

public class RecetaRoute {
    private final RecetaControl recetaControl;
    public RecetaRoute(RecetaControl recetaControl) {
        this.recetaControl=recetaControl;
    }

    public void register(Javalin app){
        app.get("/recetas", recetaControl::verRecetas);

    }
}
