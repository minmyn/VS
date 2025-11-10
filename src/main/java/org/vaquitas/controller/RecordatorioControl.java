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
        this.recordatorioService=recordatorioService;
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
}
