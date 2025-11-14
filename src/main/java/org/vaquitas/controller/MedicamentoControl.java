package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Medicamento;
import org.vaquitas.service.MedicamentoService;
import org.vaquitas.util.Error;
import org.vaquitas.util.MedicamentoValidator;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MedicamentoControl{
    private final MedicamentoService medicamentoService;
    private final MedicamentoValidator medicamentoValidator; // Se mantiene en el controlador

    public MedicamentoControl(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
        this.medicamentoValidator = new MedicamentoValidator(); // Se instancia en el constructor
    }

    public void registrarMedicamento(Context context){
        try {
            Medicamento nuevoMedicamento = context.bodyAsClass(Medicamento.class);

            // 1. Validación en el Control
            Map<String, String> errores = medicamentoValidator.validarMedicamento(nuevoMedicamento);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            medicamentoService.registrarMedicina(nuevoMedicamento);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", nuevoMedicamento);
            context.status(201).json(respuesta);

        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

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

            Optional<Medicamento> medicamentoExistente = medicamentoService.buscarMedicamentoPorId(idMedicamento);
            if (medicamentoExistente.isEmpty()) {
                context.status(404).json(Map.of("estado", false));
                return;
            }

            int affectedRows = medicamentoService.actualizarMedicamento(medicamentoActualizado);

            if (affectedRows > 0) {
                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("estado", true);
                respuesta.put("data", medicamentoActualizado);
                context.status(200).json(respuesta);
            } else {
                context.status(500).json(Error.getApiServiceError()); // Error inesperado
            }
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID del medicamento debe ser un número válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}