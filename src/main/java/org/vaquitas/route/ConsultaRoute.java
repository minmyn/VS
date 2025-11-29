package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.ConsultaControl;

public class ConsultaRoute {
    private final ConsultaControl consultaControl;
    public ConsultaRoute(ConsultaControl consultaControl) {
        this.consultaControl=consultaControl;
    }
    public void register(Javalin app){
        app.get("/consuta/{idArete}",consultaControl::buscarConsultasPorAnimal);
    }

}
