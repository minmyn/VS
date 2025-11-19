package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Reporte;
import org.vaquitas.service.ReporteServicee;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.List;

public class ReporteControl {
    private final ReporteServicee reporteServicee;

    public ReporteControl (ReporteServicee reporteServicee){
        this.reporteServicee = reporteServicee;
    }

    public void generarReporteRegistros(Context context){
        try {
            List<Reporte> reporteReguistro = reporteServicee.reporteGanado();
            context.status(200).json(reporteReguistro);
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void generarReporteAlimentos(Context context){
        try {
            List<Reporte> reporteAlimento = reporteServicee.reporteAlimento();
            context.status(200).json(reporteAlimento);
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void generarReporteVentas(Context context){
        try {
            List<Reporte> reporteVentas = reporteServicee.reporteVenta();
            context.status(200).json(reporteVentas);
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}
