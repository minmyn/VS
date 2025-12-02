package org.vaquitas.util;

import org.vaquitas.model.Recordatorio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RecordatorioValidator {
    /**
     * Valida el objeto Recordatorio.
     * @param recordatorio El objeto Recordatorio a validar.
     * @return Un mapa de errores (nombre del campo, mensaje de error).
     */
    public Map<String, String> validarRecordatorio(Recordatorio recordatorio){
        Map<String, String> errores = new HashMap<>();

        // 1. Validar que la fecha no sea nula o futura
        if (recordatorio.getFechaRecordatorio() == null) {
            errores.put("fechaRecordatorio", "La fecha del recordatorio no puede estar vacía.");
        } else {
            LocalDate hoy = LocalDate.now();
            if (recordatorio.getFechaRecordatorio().isBefore(hoy)) {
                errores.put("fechaRecordatorio", "La fecha del recordatorio no puede ser anterior a la fecha actual.");
            }
        }

        return errores;
    }
}