package org.vaquitas.util;

import org.vaquitas.model.Consulta;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase de utilidad responsable de validar los datos de la entidad {@link Consulta}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class ConsultaValidator {
    /**
     * Valida los campos obligatorios de un objeto {@link Consulta}.
     * <p>
     * Reglas de validación:
     * <ul>
     * <li>El objeto {@code Ganado} (Animal) no puede ser nulo.</li>
     * <li>El {@code Padecimiento} no puede ser nulo o estar en blanco.</li>
     * </ul>
     * </p>
     *
     * @param consulta El objeto Consulta a validar.
     * @return Un {@code Map<String, String>} con errores de validación. Retorna un mapa vacío si es válido.
     */
    public Map<String, String> validarConsulta(Consulta consulta){
        Map<String, String> errores = new HashMap<>();

        if (consulta.getGanado() == null )
            errores.put("consulta", "Verifique el campo: El animal asociado es obligatorio.");

        if (consulta.getPadecimiento() == null || consulta.getPadecimiento().isBlank())
            errores.put("padecimiento", "El padecimiento no puede estar vacío.");

        return errores;
    }
}