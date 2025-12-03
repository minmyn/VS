package org.vaquitas.util;

import org.vaquitas.model.Raza;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para validar los datos de la entidad {@link Raza}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RazaValidator {
    /**
     * Valida que el campo nombreRaza no esté vacío.
     *
     * @param raza El objeto {@link Raza} a validar.
     * @return Un mapa de errores. Retorna un mapa vacío si no hay errores.
     */
    public Map<String, String> validarRaza(Raza raza){
        Map<String, String> errores = new HashMap<>();
        if (raza.getNombreRaza() == null || raza.getNombreRaza().isBlank())
            errores.put("nombreRaza", "Verifique el campo nombre de la raza.");
        return errores;
    }
}