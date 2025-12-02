package org.vaquitas.util;

import org.vaquitas.model.Medicamento;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para validar los datos de la entidad {@link Medicamento}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class MedicamentoValidator {

    /**
     * Valida que los campos requeridos para la creación o actualización de un medicamento sean válidos.
     *
     * @param medicamento El objeto {@link Medicamento} a validar.
     * @return Un mapa de errores, donde la clave es el nombre del campo y el valor es el mensaje de error.
     * Retorna un mapa vacío si no hay errores.
     */
    public Map<String, String> validarMedicamento(Medicamento medicamento){
        Map<String, String> errores = new HashMap<>();
        if (medicamento.getNombre() == null || medicamento.getNombre().isBlank())
            errores.put("nombre", "Verifique el campo");
        if (medicamento.getDescripcion() == null || medicamento.getDescripcion().isBlank())
            errores.put("descripcion", "Verifique el campo");
        return errores;
    }
}