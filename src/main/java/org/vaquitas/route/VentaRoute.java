package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.VentaControl;
import org.vaquitas.model.Venta;

/**
 * Clase de enrutamiento que define todos los {@code endpoints} relacionados con la gestión de {@link Venta}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link VentaControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class VentaRoute {
    private final VentaControl ventaControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param ventaControl El controlador que maneja la lógica de la petición.
     */
    public VentaRoute(VentaControl ventaControl) {
        this.ventaControl = ventaControl;
    }

    /**
     * Registra todos los {@code endpoints} de ventas en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Venta</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/ventas/{id}</td><td>Registra la venta de un animal especificado por su arete ID ({@code id} es el arete).</td></tr>
     * <tr><td>GET</td><td>/ventas</td><td>Recupera el listado completo de animales vendidos (incluye detalles de la venta y el animal).</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register (Javalin app){
        app.post("/ventas/{id}", ventaControl::registrarVenta);
        app.get("/ventas", ventaControl::verVentas);
        app.patch("/ventas", ventaControl::actualizarVenta);
    }
}