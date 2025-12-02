package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.AlimentoControl;

/**
 * Clase de enrutamiento que define todos los {@code endpoints} relacionados con la gestión de {@link org.vaquitas.model.Alimento}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link AlimentoControl} para realizar operaciones CRUD.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AlimentoRoute {
    private final AlimentoControl alimentoControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param alimentoControl El controlador que maneja la lógica de la petición.
     */
    public AlimentoRoute(AlimentoControl alimentoControl) {this.alimentoControl = alimentoControl;}

    /**
     * Registra todos los {@code endpoints} de alimentos en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Alimentos</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/alimentos</td><td>Registra una nueva compra de alimento.</td></tr>
     * <tr><td>GET</td><td>/alimentos</td><td>Visualiza todas las compras de alimentos registradas.</td></tr>
     * <tr><td>PATCH</td><td>/alimentos/{id}</td><td>Actualiza la información de una compra por su ID.</td></tr>
     * <tr><td>DELETE</td><td>/alimentos/{id}</td><td>Elimina un registro de compra por su ID.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.post("/alimentos", alimentoControl::guardarAlimentos);
        app.get("/alimentos", alimentoControl::verAlimentos);
        app.patch("/alimentos/{id}", alimentoControl::editarAlimentos);
        app.delete("/alimentos/{id}", alimentoControl::eliminarAlimento);
    }
}