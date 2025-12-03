package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.ConsultaControl;
import org.vaquitas.model.Consulta;

/**
 * Clase de enrutamiento que define los {@code endpoints} relacionados con la gestión de {@link Consulta}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link ConsultaControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class ConsultaRoute {
    private final ConsultaControl consultaControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param consultaControl El controlador que maneja la lógica de la petición para Consultas.
     */
    public ConsultaRoute(ConsultaControl consultaControl) {
        this.consultaControl=consultaControl;
    }

    /**
     * Registra todos los {@code endpoints} de Consulta en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Consulta</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>GET</td><td>/consultas/animal/{idArete}</td><td>Recupera el historial de consultas médicas de un animal por su ID de arete.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.get("/consultas/animal/{idArete}", consultaControl::buscarConsultasPorAnimal);
    }
}