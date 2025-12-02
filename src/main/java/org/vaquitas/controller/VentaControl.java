package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Venta;
import org.vaquitas.service.VentaService;
import org.vaquitas.util.Error;
import org.vaquitas.util.VentaValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de {@code Venta} de ganado.
 * <p>
 * Maneja la recepción de peticiones HTTP, la validación de entrada y la coordinación con la capa de servicio.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class VentaControl {
    private final VentaService ventaService;

    /**
     * Constructor que inyecta la dependencia del servicio.
     *
     * @param ventaService El servicio de negocio para las ventas.
     */
    public VentaControl(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    /**
     * Registra la venta de un animal, iniciando el proceso de baja.
     * <p>
     * Procesa la petición POST a /ventas/{id}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void registrarVenta(Context context){
        try{
            int idArete = Integer.parseInt(context.pathParam("id"));
            Venta nuevaVenta = context.bodyAsClass(Venta.class);
            Animal animal = new Animal();
            animal.setIdArete(idArete);
            nuevaVenta.setGanado(animal); // Asocia el ID del arete del path al objeto Venta

            VentaValidator ventaValidator = new VentaValidator();
            Map<String,String> errores = ventaValidator.validarVenta(nuevaVenta);

            if (!errores.isEmpty()){
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            ventaService.registrarVenta(nuevaVenta);

            context.status(201).json(Map.of("estado", true, "mensaje", "Venta registrada con éxito", "data", nuevaVenta));
        }catch (NumberFormatException e){
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID del arete debe ser un número entero válido."));
        } catch (IllegalArgumentException e){
            // Manejo de errores de negocio (Ganado inexistente, vendido, fechas inválidas)
            context.status(404).json(Map.of("estado", false, "mensaje", e.getMessage()));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera el listado de todos los animales que han sido vendidos.
     * <p>
     * Procesa la petición GET a /ventas.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verVentas(Context context){
        try{
            List<Venta> ganadoVendido = ventaService.verVentas();
            context.status(200).json(ganadoVendido);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}