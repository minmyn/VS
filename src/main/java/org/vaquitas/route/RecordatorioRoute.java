package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RecordatorioControl;

public class RecordatorioRoute {
    private  final RecordatorioControl recordatorioControl;
    public RecordatorioRoute(RecordatorioControl recordatorioControl) {
        this.recordatorioControl=recordatorioControl;
    }

    public void register(Javalin app){
        app.get("/recordatorios", recordatorioControl::verRecordatorio);
        app.patch("/recordatorios/{id}", recordatorioControl::actualizarRecordatorio);
    }
}
