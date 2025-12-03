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
     *
     * @param ranchoService El servicio que contiene la lógica de negocio para Rancho.
     */
    public RanchoControl(RanchoService ranchoService) {
        this.ranchoService = ranchoService;
    }

    /**
     * Agrega un nuevo rancho tras validar sus campos.
     * <p>Procesa la petición POST a {@code /ranchos}.</p>
     * <ul>
     * <li>**Éxito (201 Created):** Rancho registrado.</li>
     * <li>**Error (400 Bad Request):** Validación de datos de entrada fallida.</li>
     * <li>**Error (409 Conflict):** Nombre del rancho duplicado.</li>
     * <li>**Error (500 Internal Server Error):** Fallo de base de datos o error inesperado.</li>
     * </ul>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void agregarRancho(Context context){
        try {
            Rancho nuevoRancho = context.bodyAsClass(Rancho.class);
            RanchoValidator ranchoValidator = new RanchoValidator();

            // Validación de campos
            Map<String, String> errores = ranchoValidator.validarRancho(nuevoRancho);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            // Lógica de negocio (incluye validación de duplicados)
            ranchoService.agregarRancho(nuevoRancho);

            context.status(201).json(Map.of("estado", true, "mensaje", "Rancho agregado con éxito", "data", nuevoRancho));
        }catch (IllegalArgumentException e){
            // Captura el error de nombre duplicado (409 Conflict)
            context.status(409).json(Map.of("estado", false, "mensaje", "El nombre del rancho ya está en uso."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera el listado completo de ranchos.
     * <p>Procesa la petición GET a {@code /ranchos}.</p>
     * <ul>
     * <li>**Éxito (200 OK):** Retorna la lista de ranchos.</li>
     * <li>**Error (500 Internal Server Error):** Fallo de base de datos o error inesperado.</li>
     * </ul>
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
     * <p>Procesa la petición PATCH a {@code /ranchos/{id}}.</p>
     * <ul>
     * <li>**Éxito (204 No Content):** Actualización exitosa.</li>
     * <li>**Error (400 Bad Request):** ID de rancho inválido.</li>
     * <li>**Error (500 Internal Server Error):** Fallo de base de datos o error inesperado.</li>
     * </ul>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void editarRancho(Context context){
        try {
            // 1. Extraer y validar ID del path
            int idRancho = Integer.parseInt(context.pathParam("id"));

            // 2. Mapear cuerpo y asignar ID
            Rancho editarRancho = context.bodyAsClass(Rancho.class);
            editarRancho.setIdRancho(idRancho);

            // Nota: Se podría añadir validación de campos del body aquí si el PATCH lo requiere.

            // 3. Lógica de negocio
            ranchoService.editarRancho(editarRancho);

            // 4. Respuesta de éxito
            context.status(204); // 204 No Content, exitoso sin cuerpo de respuesta.
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID de rancho debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}