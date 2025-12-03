package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RanchoControl;
import org.vaquitas.model.Rancho;

/**
 * Clase de enrutamiento que define todos los {@code endpoints} relacionados con la gestión del catálogo de {@link Rancho}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link RanchoControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RanchoRoute {
    private final RanchoControl ranchoControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param ranchoControl El controlador que maneja la lógica de la petición.
     */
    public RanchoRoute(RanchoControl ranchoControl) {
        this.ranchoControl = ranchoControl;
    }

    /**
     * Registra todos los {@code endpoints} de rancho en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Rancho</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/ranchos</td><td>Registra un nuevo rancho en el catálogo.</td></tr>
     * <tr><td>GET</td><td>/ranchos</td><td>Recupera el listado completo de ranchos.</td></tr>
     * <tr><td>PATCH</td><td>/ranchos/{id}</td><td>Actualiza nombre y ubicación de un rancho por su ID.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.post("/ranchos", ranchoControl::agregarRancho);
        app.get("/ranchos", ranchoControl::verRanchos);
        app.patch("/ranchos/{id}", ranchoControl::editarRancho);
    }
}