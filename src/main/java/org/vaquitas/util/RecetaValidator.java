package org.vaquitas.util;

import org.vaquitas.model.DTOreceta; // Importar el nuevo DTO
import java.util.HashMap;
import java.util.Map;

public class RecetaValidator {

    public Map<String, String> validarCreacionDTO(DTOreceta dto){
        Map<String, String> errores = new HashMap<>();

        if (dto.getIdMedicamento() <= 0)
            errores.put("idMedicamento", "ID de medicamento inválido.");

        if (dto.getIdAreteGanado() <= 0)
            errores.put("idAreteGanado", "ID de arete inválido.");

        if (dto.getPadecimiento() == null || dto.getPadecimiento().isBlank())
            errores.put("padecimiento", "El padecimiento es obligatorio.");

        if (dto.getFechaRecordatorio() == null)
            errores.put("fechaRecordatorio", "La fecha de recordatorio es obligatoria.");

        if (dto.getFechaInicio() == null)
            errores.put("fechaInicio", "La fecha de inicio de la receta es obligatoria.");

        if (dto.getDosis() <= 0)
            errores.put("dosis", "La dosis debe ser mayor a cero.");

        return errores;
    }
}