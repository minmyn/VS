package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Rancho;
import org.vaquitas.service.RanchoService;
import org.vaquitas.util.Error;
import org.vaquitas.util.RanchoValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de {@link Rancho}.
 * <p>
 * Maneja la recepción de peticiones HTTP, la validación de entrada y la coordinación con la capa de servicio.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RanchoControl {
    private final RanchoService ranchoService;

    /**
     * Constructor que inyecta la dependencia del servicio de ranchos.
     */
    public RanchoControl(RanchoService ranchoService) {
        this.ranchoService = ranchoService;
    }

    /**
     * Agrega un nuevo rancho tras validar sus campos.
     * <p>
     * Procesa la petición POST a /ranchos.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void agregarRancho(Context context){
        try {
            Rancho nuevoRancho = context.bodyAsClass(Rancho.class);
            RanchoValidator ranchoValidator = new RanchoValidator();
            Map<String, String> errores = ranchoValidator.validarRancho(nuevoRancho);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }
            ranchoService.agregarRancho(nuevoRancho);
            context.status(201).json(Map.of("estado", true, "data", nuevoRancho));
        }catch (IllegalArgumentException e){
            // Captura el error de nombre duplicado lanzado desde el servicio
            context.status(409).json(Map.of("estado", false, "error", "El nombre del rancho ya está en uso."));
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera el listado completo de ranchos.
     * <p>
     * Procesa la petición GET a /ranchos.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verRanchos(Context context){
        try {
            List<Rancho> ranchos = ranchoService.verRanchos();
            context.status(200).json(ranchos);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Actualiza el nombre y/o la ubicación de un rancho por su ID.
     * <p>
     * Procesa la petición PATCH a /ranchos/{id}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void editarRancho(Context context){
        try {
            int idRancho = Integer.parseInt(context.pathParam("id"));
            Rancho editarRancho = context.bodyAsClass(Rancho.class);
            editarRancho.setIdRancho(idRancho);
            ranchoService.editarRancho(editarRancho);
            context.status(204); // 204 No Content, exitoso sin cuerpo de respuesta.
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "error", "ID de rancho inválido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}