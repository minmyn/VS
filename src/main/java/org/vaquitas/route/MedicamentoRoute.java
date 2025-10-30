package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.MedicamentoControl;

public class MedicamentoRoute {
    private final MedicamentoControl medicamentoControl;
    public MedicamentoRoute(MedicamentoControl medicamentoControl) {
        this.medicamentoControl=medicamentoControl;
    }
    public void register(Javalin app){
        app.post("/medicamento", medicamentoControl::registrarMedicamento);
        app.get("/medicamento", medicamentoControl::verMedicinas);
        app.patch("/medicamento/{id}", medicamentoControl::actualizarMedicamento);
    }
}