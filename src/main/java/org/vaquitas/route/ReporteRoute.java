package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.ReporteControl;

public class ReporteRoute {
    private final ReporteControl reporteControl;

    public ReporteRoute(ReporteControl reporteControl) {
        this.reporteControl = reporteControl;
    }

    public void register(Javalin app){
        app.get("/reportes/registros", reporteControl::generarReporteRegistros);
        app.get("/reportes/alimentos", reporteControl::generarReporteAlimentos);
        app.get("/reportes/ventas", reporteControl::generarReporteVentas);
    }
}
