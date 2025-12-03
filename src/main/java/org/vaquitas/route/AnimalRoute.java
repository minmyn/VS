package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.AnimalControl;
import org.vaquitas.model.Animal;

/**
 * Clase de enrutamiento que define todos los {@code endpoints} relacionados con la gestión de {@link Animal} (Ganado).
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link AnimalControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AnimalRoute {
    private final AnimalControl animalControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param animalControl El controlador que maneja la lógica de la petición.
     */
    public AnimalRoute(AnimalControl animalControl){
        this.animalControl=animalControl;
    }

    /**
     * Registra todos los {@code endpoints} de ganado en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Ganado</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/ganado</td><td>Registra un nuevo animal.</td></tr>
     * <tr><td>GET</td><td>/ganado</td><td>Visualiza todos los animales registrados (activos e inactivos).</td></tr>
     * <tr><td>GET</td><td>/ganado/activos</td><td>Visualiza solo los animales con estatus 'Activo'.</td></tr>
     * <tr><td>GET</td><td>/ganado/no-activos</td><td>Visualiza solo los animales con estatus 'Muerto' o 'Vendido'.</td></tr>
     * <tr><td>GET</td><td>/ganado/vendidos</td><td>Visualiza solo los animales con estatus 'Vendido'.</td></tr>
     * <tr><td>GET</td><td>/ganado/{id}</td><td>Visualiza un animal específico por su ID de arete.</td></tr>
     * <tr><td>PATCH</td><td>/ganado/{id}</td><td>Da de baja (Muerto o Vendido) un animal específico.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.post("/ganado", animalControl::registrarGanado);
        app.get("/ganado", animalControl::visualizarGanado);
        app.get("/ganado/activos", animalControl::vizualizarGanadoActivo);
        app.get("/ganado/no-activos", animalControl::visualizarGanadoNoActivo);
        app.get("/ganado/vendidos", animalControl::visualizarGanadoVendido);
        app.get("/ganado/{id}", animalControl::verUnGanado);
        app.patch("/ganado/{id}", animalControl::darBajaGanado);
    }
}