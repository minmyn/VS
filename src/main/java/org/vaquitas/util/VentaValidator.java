package org.vaquitas.util;

import org.vaquitas.model.Venta;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase utilitaria para validar los datos de entrada para la entidad {@link Venta}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class VentaValidator {
    /**
     * Valida que los campos requeridos para la venta sean válidos.
     *
     * @param venta El objeto {@link Venta} a validar.
     * @return Un mapa de errores. Retorna un mapa vacío si no hay errores.
     */
    public Map<String, String> validarVenta(Venta venta){
        Map<String, String> errores = new HashMap<>();

        if (venta.getGanado() == null || venta.getGanado().getIdArete() <= 0)
            errores.put("idArete", "El ID del arete es inválido o no está especificado.");

        if (venta.getFechaBaja() == null) {
            errores.put("fechaBaja", "La fecha de baja es obligatoria");
        } else if (venta.getFechaBaja().isAfter(LocalDate.now())) {
            errores.put("fechaBaja", "La fecha de baja no puede ser futura");
        }

        if (venta.getPesoFinal() <= 0 )
            errores.put("pesoFinal", "El peso final debe ser mayor a 0.");

        if (venta.getPrecioVenta() <= 0 )
            errores.put("precioVenta", "El precio de venta debe ser mayor a 0.");

        return errores;
    }
}