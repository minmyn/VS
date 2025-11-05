package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.AlimentoControl;

public class AlimentoRoute {
    private final AlimentoControl alimentoControl;
    public AlimentoRoute(AlimentoControl alimentoControl) {this.alimentoControl = alimentoControl;}
    public void register(Javalin app){
        app.post("/alimentos", alimentoControl::guardarAlimentos);
        app.get("/alimentos", alimentoControl::verAlimentos);
        app.patch("/alimentos/{id}", alimentoControl::editarAlimentos);
    }
}
