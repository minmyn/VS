package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Consulta;
import org.vaquitas.util.Error;
import org.vaquitas.service.ConsultaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Controlador para la gestión de {@link Consulta} médica de un animal.
 * <p>
 * Maneja las peticiones HTTP para buscar información sobre consultas.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class ConsultaControl {

    private final ConsultaService consultaService;

    /**
     * Constructor que inyecta la dependencia del servicio de consultas.
     *
     * @param consultaService El servicio que contiene la lógica de negocio para Consulta.
     */
    public ConsultaControl(ConsultaService consultaService) {
        this.consultaService=consultaService;
    }


    /**
     * Busca y recupera todas las consultas médicas asociadas a un animal específico.
     * <p>
     * Procesa la petición GET a /consultas/animal/{idArete}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void buscarConsultasPorAnimal(Context context) {
        try {
            // Se obtiene el ID del arete desde el path
            int areteId = Integer.parseInt(context.pathParam("idArete"));

            // Se llama al servicio para obtener la lista de consultas
            List<Consulta> consultas = consultaService.obtenerConsultasPorArete(areteId);

            if (consultas.isEmpty()) {
                context.status(404).json(Map.of("estado", false, "mensaje", "No se encontraron consultas para el arete ID: " + areteId));
                return;
            }

            context.status(200).json(consultas);
        } catch (NumberFormatException e) {
            // Manejo de error 400 (Bad Request) para IDs no numéricos
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID del arete debe ser un número entero válido (BIGINT)."));
        } catch (SQLException e) {
            // Manejo de error 500 (Internal Server Error) para fallos de base de datos
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            // Manejo de error 500 (Internal Server Error) para otros fallos de servicio
            context.status(500).json(Error.getApiServiceError());
        }
    }
}