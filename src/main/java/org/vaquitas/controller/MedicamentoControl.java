package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Medicamento;
import org.vaquitas.service.MedicamentoService;
import org.vaquitas.util.Error;
import org.vaquitas.util.MedicamentoValidator;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de {@link Medicamento}.
 * <p>
 * Maneja la recepción de peticiones HTTP (POST, GET, PATCH), la validación de entrada
 * y la coordinación con la capa de servicio para el catálogo de medicamentos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class MedicamentoControl{
    private final MedicamentoService medicamentoService;
    private final MedicamentoValidator medicamentoValidator;

    /**
     * Constructor que inyecta las dependencias del servicio y el validador.
     *
     * @param medicamentoService El servicio de negocio para los medicamentos.
     */
    public MedicamentoControl(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
        this.medicamentoValidator = new MedicamentoValidator();
    }

    /**
     * Registra un nuevo medicamento.
     * <p>Procesa la petición POST a {@code /medicamentos}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void registrarMedicamento(Context context){
        try {
            Medicamento nuevoMedicamento = context.bodyAsClass(Medicamento.class);
            Map<String, String> errores = medicamentoValidator.validarMedicamento(nuevoMedicamento);

            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            medicamentoService.registrarMedicina(nuevoMedicamento);

            context.status(201).json(Map.of("estado", true, "mensaje", "Medicamento registrado con éxito", "data", nuevoMedicamento));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera el listado completo de medicamentos.
     * <p>Procesa la petición GET a {@code /medicamentos}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verMedicinas(Context context){
        try {
            List<Medicamento> medicamentos = medicamentoService.verMedicinas();
            context.status(200).json(medicamentos);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Busca medicamentos por texto en el nombre.
     * <p>Procesa la petición GET a {@code /medicamento?nombre={texto}}. El parámetro se obtiene del query.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void buscarMedicamentosPorAlgo(Context context){
        String texto = context.queryParam("nombre");
        try {
            List<Medicamento> medicamentos = medicamentoService.buscarMedicamentos(texto);
            context.status(200).json(medicamentos);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Actualiza un medicamento existente.
     * <p>Procesa la petición PATCH a {@code /medicamentos/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void actualizarMedicamento(Context context){
        try {
            int idMedicamento = Integer.parseInt(context.pathParam("id"));

            Medicamento medicamentoActualizado = context.bodyAsClass(Medicamento.class);
            medicamentoActualizado.setIdMedicamento(idMedicamento);

            Map<String, String> errores = medicamentoValidator.validarMedicamento(medicamentoActualizado);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            int affectedRows = medicamentoService.actualizarMedicamento(medicamentoActualizado);

            if (affectedRows > 0) {
                context.status(200).json(Map.of("estado", true, "mensaje", "Medicamento actualizado con éxito", "data", medicamentoActualizado));
            } else {
                context.status(404).json(Map.of("estado", false, "mensaje", "Medicamento no encontrado para actualizar."));
            }
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID del medicamento debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}