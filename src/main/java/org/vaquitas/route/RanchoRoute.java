package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RanchoControl;

public class RanchoRoute {
    private final RanchoControl ranchoControl;
    public RanchoRoute(RanchoControl ranchoControl) {
        this.ranchoControl = ranchoControl;
    }

    public void register(Javalin app){
        app.post("/ranchos", ranchoControl::agregarRancho);
        app.get("/ranchos", ranchoControl::verRanchos);
        app.patch("/ranchos/{id}", ranchoControl::editarRancho);
    }
}