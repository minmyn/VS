package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.service.RecordatorioService;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.List;

public class RecordatorioControl {
    private final RecordatorioService recordatorioService;

    public RecordatorioControl(RecordatorioService recordatorioService) {
        this.recordatorioService = recordatorioService;
    }

    public void verRecordatorio(Context context) {
        try {
            List<Recordatorio> recordatorios = recordatorioService.verRecordatorio();
            context.status(200).json(recordatorios);
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void actualizarRecordatorio(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("id"));
            Recordatorio recordatorioAct = context.bodyAsClass(Recordatorio.class);
            recordatorioAct.setIdRecordatorio(id);
            recordatorioService.editarRecordatorio(recordatorioAct);
            context.status(204);
        } catch (NumberFormatException e) {
            context.status(400).result("ID de recordatorio no válido.");
        } catch (Exception e) {
            context.status(500).result("Error al actualizar el recordatorio: " + e.getMessage());
        }
    }
}