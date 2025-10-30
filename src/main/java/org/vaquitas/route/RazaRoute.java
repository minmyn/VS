package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RazaControl;

public class RazaRoute {
    private final RazaControl razaControl;
    public RazaRoute(RazaControl razaControl) {
        this.razaControl = razaControl;
    }

    public void register(Javalin app){
        app.post("/razas", razaControl::registrarRaza);
        app.get("/razas", razaControl::verRaza);
        app.patch("/razas/{id}", razaControl::renombrarRaza);

    }
}