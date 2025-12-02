package org.vaquitas.util;

import org.vaquitas.model.Rancho;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para validar los datos de la entidad {@link Rancho}.
 * <p>
 * Incluye validación de campos obligatorios.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RanchoValidator {

    /**
     * Valida que los campos obligatorios del rancho (nombre y ubicación) no estén vacíos.
     *
     * @param rancho El objeto {@link Rancho} a validar.
     * @return Un mapa de errores, donde la clave es el nombre del campo y el valor es el mensaje de error.
     * Retorna un mapa vacío si no hay errores.
     */
    public Map<String, String> validarRancho(Rancho rancho){
        Map<String, String> errores = new HashMap<>();

        if (rancho.getNombre() == null || rancho.getNombre().isBlank())
            errores.put("nombre", "Verifique el campo nombre");

        if (rancho.getUbicacion() == null || rancho.getUbicacion().isBlank())
            errores.put("ubicacion", "Verifique el campo ubicación");

        return errores;
    }
}