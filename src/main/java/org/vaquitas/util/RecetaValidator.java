package org.vaquitas.util;

import org.vaquitas.model.DTOreceta;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de utilidad para validar los datos de entrada contenidos en el {@link DTOreceta}
 * antes de proceder a la creación de las entidades relacionadas (Consulta, Receta, Recordatorio).
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecetaValidator {

    /**
     * Valida los campos obligatorios para la creación de una nueva receta.
     *
     * @param dto El DTO de receta a validar.
     * @return Un {@code Map<String, String>} con errores de validación. Retorna un mapa vacío si es válido.
     */
    public Map<String, String> validarCreacionDTO(DTOreceta dto){
        Map<String, String> errores = new HashMap<>();

        if (dto.getIdMedicamento() < 0)
            errores.put("idMedicamento", "ID de medicamento inválido.");

        if (dto.getIdAreteGanado() < 0)
            errores.put("idAreteGanado", "ID de arete inválido.");

        if (dto.getPadecimiento() == null || dto.getPadecimiento().isBlank())
            errores.put("padecimiento", "El padecimiento es obligatorio.");

        if (dto.getFechaRecordatorio() == null) {
            errores.put("fechaRecordatorio", "La fecha de recordatorio es obligatoria.");
        }else if (dto.getFechaRecordatorio().isBefore(LocalDate.now())) {
            errores.put("fechaRecordatorio", "La fecha del recordatorio no puede ser anterior a la fecha actual.");
        }

        if (dto.getFechaInicio() == null){
            errores.put("fechaInicio", "La fecha de inicio de la receta es obligatoria.");
        }else if (dto.getFechaInicio().isAfter(LocalDate.now())) {
            errores.put("fechaInicio", "La fecha de inicio no puede ser futura.");
        }

        if (dto.getDosis() < 0)
            errores.put("dosis", "La dosis debe ser mayor a cero.");

        return errores;
    }
}