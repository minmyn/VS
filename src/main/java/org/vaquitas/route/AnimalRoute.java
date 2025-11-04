package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.AnimalControl;

public class AnimalRoute {
    private final AnimalControl animalControl;
    public AnimalRoute(AnimalControl animalControl){
        this.animalControl=animalControl;
    }
    public void register(Javalin app){
        app.post("/ganado", animalControl::registrarGanado);
        app.get("/ganado", animalControl::visualizarGanado);
        app.get("/ganado/activos", animalControl::vizualizarGanadoActivo);
        app.get("/ganado/no-activos", animalControl::visualizarGanadoNoActivo);
        app.get("/ganado/vendidos", animalControl::visualizarGanadoVendido);
        app.patch("/ganado/{id}", animalControl::darBajaGanado);
    }
}