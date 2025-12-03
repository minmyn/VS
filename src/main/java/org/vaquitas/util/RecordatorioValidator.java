package org.vaquitas.util;

import org.vaquitas.model.Recordatorio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para validar los datos de la entidad {@link Recordatorio}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecordatorioValidator {
    /**
     * Valida el objeto Recordatorio, asegurando que su fecha no sea nula o pasada.
     * @param recordatorio El objeto Recordatorio a validar.
     * @return Un mapa de errores. Retorna un mapa vacío si es válido.
     */
    public Map<String, String> validarRecordatorio(Recordatorio recordatorio){
        Map<String, String> errores = new HashMap<>();

        if (recordatorio.getFechaRecordatorio() == null) {
            errores.put("fechaRecordatorio", "La fecha del recordatorio no puede estar vacía.");
        } else if (recordatorio.getFechaRecordatorio().isBefore(LocalDate.now())) {
            errores.put("fechaRecordatorio", "La fecha del recordatorio no puede ser anterior a la fecha actual.");
        }

        return errores;
    }
}