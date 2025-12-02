package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.*;
import org.vaquitas.service.MedicamentoService;
import org.vaquitas.service.RecetaService;
import org.vaquitas.util.Error;
import org.vaquitas.util.RecetaValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de {@code Recetas} de salud e higiene animal.
 * <p>
 * Maneja la lógica de validación del DTO de entrada y la coordinación de la lógica de negocio
 * para la creación de las entidades {@link Consulta}, {@link Receta} y {@link Recordatorio}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecetaControl {
    private final RecetaService recetaService;
    private final RecetaValidator recetaValidator;

    /**
     * Constructor que inyecta las dependencias del servicio y el validador.
     *
     * @param recetaService El servicio de negocio para las recetas.
     */
    public RecetaControl(RecetaService recetaService) {
        this.recetaService = recetaService;
        this.recetaValidator = new RecetaValidator();
    }

    /**
     * Crea un nuevo registro de salud/higiene animal, que implica la creación de una Consulta,
     * un Recordatorio (si no existe) y la Receta.
     * <p>
     * Procesa la petición POST a /receta.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void guardarSaludHigiene(Context context) {
        try {
            DTOreceta dto = context.bodyAsClass(DTOreceta.class);
            Map<String, String> errores = recetaValidator.validarCreacionDTO(dto);

            if (!errores.isEmpty()){
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            // Mapeo del DTO a las entidades de dominio
            Medicamento medicamento = new Medicamento();
            medicamento.setIdMedicamento(dto.getIdMedicamento());

            Animal ganado = new Animal();
            ganado.setIdArete(dto.getIdAreteGanado());

            Consulta consulta = new Consulta();
            consulta.setGanado(ganado);
            consulta.setPadecimiento(dto.getPadecimiento());

            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setFechaRecordatorio(dto.getFechaRecordatorio());

            Receta receta = new Receta();
            receta.setMedicamento(medicamento);
            receta.setConsulta(consulta);
            receta.setRecordatorio(recordatorio);
            receta.setDosis(dto.getDosis());
            receta.setFechaInicio(dto.getFechaInicio());

            // Ejecutar servicio
            recetaService.guardarReceta(receta);

            context.status(201).json(Map.of("estado", true, "data", dto));
        } catch (IllegalArgumentException e) {
            // Manejo de errores de negocio (e.g., Ganado no activo, Medicamento no existe)
            context.status(404).json(Map.of("mensaje", e.getMessage()));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera los detalles consolidados de todas las recetas.
     * <p>
     * Procesa la petición GET a /receta.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verDetallesRecetas(Context context) {
        try {
            List<DTOdetalles> detalles = recetaService.verDetallesRecetas();
            context.status(200).json(detalles);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera los detalles de las recetas asociadas a un medicamento específico.
     * <p>
     * Procesa la petición GET a /receta/medicamento/{id}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verRecetaPorMedicamento(Context context){
        try {
            int idMedicamento = Integer.parseInt(context.pathParam("id"));
            List<DTOdetalles> detalles = recetaService.verRecetaPorMedicamento(idMedicamento);
            context.status(200).json(detalles);
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("mensaje", "El ID del medicamento debe ser un número entero válido."));
        } catch (IllegalArgumentException e) {
            String mensajeError = e.getMessage();
            context.status(400).json(mensajeError);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera los detalles de las recetas asociadas a un recordatorio específico (por ID de calendario/recordatorio).
     * <p>
     * Procesa la petición GET a /receta/recordatorio/{id}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin. El parámetro 'id' es el ID del recordatorio.
     */
    public void verRecetaPorRecordatorio(Context context){
        try {
            int idMedicamento = Integer.parseInt(context.pathParam("id"));
            List<DTOdetalles> detalles = recetaService.verRecetaPorRecordatorio(idMedicamento);
            context.status(200).json(detalles);
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("mensaje", "El ID del medicamento debe ser un número entero válido."));
        } catch (IllegalArgumentException e) {
            String mensajeError = e.getMessage();
            context.status(400).json(mensajeError);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

}