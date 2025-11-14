package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.*;
import org.vaquitas.service.MedicamentoService; // Nueva Dependencia
import org.vaquitas.service.RecetaService;
import org.vaquitas.util.Error;
import org.vaquitas.util.RecetaValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RecetaControl {
    private final RecetaService recetaService;
    private final MedicamentoService medicamentoService; // Nuevo campo
    private final RecetaValidator recetaValidator;

    // Constructor actualizado para inyectar MedicamentoService
    public RecetaControl(RecetaService recetaService, MedicamentoService medicamentoService) {
        this.recetaService = recetaService;
        this.medicamentoService = medicamentoService; // Inicialización de nuevo campo
        this.recetaValidator = new RecetaValidator(); // Uso del validador actualizado
    }

    public void guardarSaludHigiene(Context context) {
        try {
            // 1. Deserializar el DTO
            DTOreceta dto = context.bodyAsClass(DTOreceta.class);

            // 2. Validación del DTO
            Map<String, String> errores = recetaValidator.validarCreacionDTO(dto);

            if (!errores.isEmpty()){
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            // 3. Obtener Modelos y Mapeo (Lógica de negocio en el controlador)
            // A. Obtener Medicamento (Debe existir)
            Medicamento medicamento = medicamentoService.buscarMedicamentoPorId(dto.getIdMedicamento())
                    .orElseThrow(() -> new IllegalArgumentException("El ID de medicamento no existe."));

            // B. Crear Animal/Ganado y Consulta
            Animal ganado = new Animal(); // Asumiendo que existe una clase Animal/Ganado
            ganado.setIdArete(dto.getIdAreteGanado());

            Consulta consulta = new Consulta();
            consulta.setGanado(ganado);
            consulta.setPadecimiento(dto.getPadecimiento());

            // C. Crear Recordatorio
            Recordatorio recordatorio = new Recordatorio();
            recordatorio.setFechaRecordatorio(dto.getFechaRecordatorio());

            // D. Crear Receta
            Receta receta = new Receta();
            receta.setMedicamento(medicamento);
            receta.setConsulta(consulta);
            receta.setRecordatorio(recordatorio);
            receta.setDosis(dto.getDosis());
            receta.setFechaInicio(dto.getFechaInicio()); // Nombre de campo corregido

            // 4. Llamar al servicio
            recetaService.guardarReceta(receta);

            context.status(201).json(Map.of("mensaje", "Guardado correctamente"));

        } catch (IllegalArgumentException e) {
            // Captura errores como "Medicamento no encontrado"
            context.status(404).json(Map.of("mensaje", e.getMessage()));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

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
}