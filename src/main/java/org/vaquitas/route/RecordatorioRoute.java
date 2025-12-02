package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RecordatorioControl;

/**
 * Clase de enrutamiento que define los {@code endpoints} relacionados con la gestión de
 * {@link org.vaquitas.model.Recordatorio}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecordatorioRoute {
    private final RecordatorioControl recordatorioControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param recordatorioControl El controlador que maneja la lógica de la petición para Recordatorios.
     */
    public RecordatorioRoute(RecordatorioControl recordatorioControl) {
        this.recordatorioControl=recordatorioControl;
    }

    /**
     * Registra todos los {@code endpoints} de Recordatorio en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Recordatorio</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>GET</td><td>/recordatorios</td><td>Recupera el listado de todos los recordatorios.</td></tr>
     * <tr><td>PATCH</td><td>/recordatorios/{id}</td><td>Actualiza la fecha de un recordatorio específico por su ID.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.get("/recordatorios", recordatorioControl::verRecordatorio);
        app.patch("/recordatorios/{id}", recordatorioControl::actualizarRecordatorio);
    }
}