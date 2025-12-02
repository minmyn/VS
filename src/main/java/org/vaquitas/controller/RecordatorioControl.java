package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.service.RecordatorioService;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de {@link Recordatorio}.
 * <p>
 * Maneja las peticiones HTTP (GET, PATCH) para visualizar y actualizar los recordatorios.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecordatorioControl {
    private final RecordatorioService recordatorioService;

    /**
     * Constructor que inyecta la dependencia del servicio de recordatorios.
     *
     * @param recordatorioService El servicio que contiene la lógica de negocio para Recordatorio.
     */
    public RecordatorioControl(RecordatorioService recordatorioService) {
        this.recordatorioService = recordatorioService;
    }

    /**
     * Recupera el listado completo de todos los recordatorios programados.
     * <p>
     * Procesa la petición GET a /recordatorios.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
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

    /**
     * Actualiza la fecha de un recordatorio específico por su ID.
     * <p>
     * Procesa la petición PATCH a /recordatorios/{id}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void actualizarRecordatorio(Context context) {
        try {
            int id = Integer.parseInt(context.pathParam("id"));
            Recordatorio recordatorioAct = context.bodyAsClass(Recordatorio.class);
            recordatorioAct.setIdRecordatorio(id);
            recordatorioService.editarRecordatorio(recordatorioAct);

            // 204 Respuesta de actualización exitosa.
            context.status(204);
        } catch (NumberFormatException e) {
            // Manejo de error 400 (Bad Request) para IDs no numéricos
            context.status(400).json(Map.of("estado", false, "error", "ID de recordatorio no válido o formato incorrecto."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            // Usar el error genérico 500 para fallos del servidor o del servicio
            context.status(500).json(Error.getApiServiceError());
        }
    }
}