package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.MedicamentoControl;

public class MedicamentoRoute {
    private final MedicamentoControl medicamentoControl;
    public MedicamentoRoute(MedicamentoControl medicamentoControl) {
        this.medicamentoControl=medicamentoControl;
    }
    public void register(Javalin app){
        app.post("/medicamentos", medicamentoControl::registrarMedicamento);
        app.get("/medicamentos", medicamentoControl::verMedicinas);
        app.get("/medicamento", medicamentoControl::buscarMedicamentosPorAlgo);
        app.patch("/medicamentos/{id}", medicamentoControl::actualizarMedicamento);
    }
}