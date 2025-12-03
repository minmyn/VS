package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Raza;
import org.vaquitas.service.RazaService;
import org.vaquitas.util.Error;
import org.vaquitas.util.RazaValidator;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión del catálogo de {@link Raza}.
 * <p>
 * Maneja las peticiones HTTP (POST, GET, PATCH) para el CRUD del catálogo de razas.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RazaControl {
    private final RazaService razaService;

    /**
     * Constructor que inyecta la dependencia del servicio de razas.
     *
     * @param razaService El servicio que contiene la lógica de negocio para Raza.
     */
    public RazaControl(RazaService razaService) {
        this.razaService=razaService;
    }

    /**
     * Registra una nueva raza en el catálogo.
     * <p>Procesa la petición POST a {@code /razas}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void registrarRaza(Context context){
        try {
            Raza nuevaRaza = context.bodyAsClass(Raza.class);
            RazaValidator razaValidator = new RazaValidator();
            Map<String, String> errores = razaValidator.validarRaza(nuevaRaza);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            // Lógica de negocio (incluye validación de duplicados)
            razaService.registrarRaza(nuevaRaza);

            // Respuesta exitosa
            context.status(201).json(Map.of("estado", true,"data", nuevaRaza));
        }catch (IllegalArgumentException e){
            // Captura de Raza duplicada (Error de negocio/duplicidad)
            context.status(409).json(Map.of("estado", false, "mensaje", "Raza duplicada. " + e.getMessage()));
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera el listado completo de todas las razas en el catálogo.
     * <p>Procesa la petición GET a {@code /razas}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void verRaza(Context context){
        try {
            List<Raza> razas = razaService.verRazas();
            context.status(200).json(razas);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Renombra o actualiza el nombre de una raza por su ID.
     * <p>Procesa la petición PATCH a {@code /razas/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP.
     */
    public void renombrarRaza(Context context){
        try {
            int idRaza = Integer.parseInt(context.pathParam("id"));
            Raza renombreRaza = context.bodyAsClass(Raza.class);
            renombreRaza.setIdRaza(idRaza);

            RazaValidator razaValidator = new RazaValidator();

            Map<String, String> errores = razaValidator.validarRaza(renombreRaza);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            razaService.renombrarRaza(renombreRaza);

            context.status(200).json(Map.of("estado", true, "mensaje", "Raza renombrada con éxito", "data", renombreRaza));
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID de la raza debe ser un número entero válido."));
        } catch (IllegalArgumentException e){
            // Captura de Raza no encontrada o error de dato
            context.status(404).json(Map.of("estado", false, "mensaje", "Raza no encontrada o error de dato: " + e.getMessage()));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}