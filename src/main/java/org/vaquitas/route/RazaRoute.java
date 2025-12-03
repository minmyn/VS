package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.RazaControl;
import org.vaquitas.model.Raza;

/**
 * Clase de enrutamiento que define los {@code endpoints} relacionados con la gestión del catálogo de {@link Raza}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link RazaControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RazaRoute {
    private final RazaControl razaControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param razaControl El controlador que maneja la lógica de la petición para Raza.
     */
    public RazaRoute(RazaControl razaControl) {
        this.razaControl = razaControl;
    }

    /**
     * Registra todos los {@code endpoints} de Raza en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Raza</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/razas</td><td>Registra una nueva raza en el catálogo.</td></tr>
     * <tr><td>GET</td><td>/razas</td><td>Recupera el listado completo del catálogo de razas.</td></tr>
     * <tr><td>PATCH</td><td>/razas/{id}</td><td>Renombra una raza específica por su ID.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.post("/razas", razaControl::registrarRaza);
        app.get("/razas", razaControl::verRaza);
        app.patch("/razas/{id}", razaControl::renombrarRaza);
    }
}