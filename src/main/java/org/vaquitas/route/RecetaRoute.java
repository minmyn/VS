package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RecetaControl;
import org.vaquitas.model.Receta;

/**
 * Clase de enrutamiento que define todos los {@code endpoints} relacionados con la gestión de {@link Receta}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link RecetaControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecetaRoute {
    private final RecetaControl recetaControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param recetaControl El controlador que maneja la lógica de la petición.
     */
    public RecetaRoute(RecetaControl recetaControl) {
        this.recetaControl=recetaControl;
    }

    /**
     * Registra todos los {@code endpoints} de recetas en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Recetas</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/receta</td><td>Crea una nueva receta, consulta y recordatorio de forma transaccional.</td></tr>
     * <tr><td>GET</td><td>/receta</td><td>Visualiza todas las recetas con detalles consolidados (DTOdetalles).</td></tr>
     * <tr><td>GET</td><td>/receta/medicamento/{id}</td><td>Visualiza recetas filtradas por el ID del medicamento.</td></tr>
     * <tr><td>GET</td><td>/receta/recordatorio/{id}</td><td>Visualiza recetas filtradas por el ID del recordatorio (calendario).</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.post("/receta", recetaControl::guardarSaludHigiene);
        app.get("/receta", recetaControl::verDetallesRecetas);
        app.get("/receta/medicamento/{id}", recetaControl::verRecetaPorMedicamento);
        app.get("/receta/recordatorio/{id}", recetaControl::verRecetaPorRecordatorio);
    }
}