package org.vaquitas.util;

import org.vaquitas.model.Alimento;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de utilidad que implementa la lógica de validación para la entidad {@link Alimento}.
 * <p>
 * Contiene métodos para validar la creación de alimentos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AlimentoValidator {

    /**
     * Valida los campos obligatorios y sus restricciones de un objeto {@link Alimento}.
     *
     * @param alimento El objeto Alimento a ser validado.
     * @return Un {@code Map<String, String>} que contiene los errores de validación.
     * Retorna un mapa vacío si no se encuentra ningún error.
     */
    public Map<String, String> validarAlimento(Alimento alimento) {
        Map<String, String> errores = new HashMap<>();

        if (alimento.getNombre() == null || alimento.getNombre().isBlank())
            errores.put("nombre", "El nombre no puede estar vacío");

        if (alimento.getTipo() == null || alimento.getTipo().isBlank())
            errores.put("tipo", "El tipo no puede estar vacío");

        if (alimento.getCantidad() < 0)
            errores.put("cantidad", "La cantidad debe ser mayor que cero");

        LocalDate fechaCompra = alimento.getFechaCompra();
        if (fechaCompra == null) {
            errores.put("fechaCompra", "La fecha de compra no puede estar vacía");
        } else if (fechaCompra.isAfter(LocalDate.now())) {
            errores.put("fechaCompra", "La fecha de compra no puede ser futura");
        }

        if (alimento.getPrecio() < 0)
            errores.put("precio", "El precio debe ser mayor que cero");

        return errores;
    }
}