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
     * <p>
     * Las reglas de validación aplicadas incluyen:
     * <ul>
     * <li>**nombre**: No puede ser nulo o estar en blanco.</li>
     * <li>**tipo**: No puede ser nulo o estar en blanco.</li>
     * <li>**cantidad**: Debe ser un valor estrictamente mayor que cero.</li>
     * <li>**fechaCompra**: No puede ser nula ni ser una fecha futura.</li>
     * <li>**precio**: Debe ser un valor estrictamente mayor que cero.</li>
     * </ul>
     * </p>
     *
     * @param alimento El objeto Alimento a ser validado.
     * @return Un {@code Map<String, String>} que contiene los errores de validación.
     * La clave es el nombre del campo que falló y el valor es el mensaje de error.
     * Retorna un mapa vacío si no se encuentra ningún error (validación exitosa).
     */
    public Map<String, String> validarAlimento(Alimento alimento) {
        Map<String, String> errores = new HashMap<>();

        // Validación de nombre
        if (alimento.getNombre() == null || alimento.getNombre().isBlank())
            errores.put("nombre", "El nombre no puede estar vacío");

        // Validación de tipo
        if (alimento.getTipo() == null || alimento.getTipo().isBlank())
            errores.put("tipo", "El tipo no puede estar vacío");

        // Validación de cantidad
        if (alimento.getCantidad() <= 0)
            errores.put("cantidad", "La cantidad debe ser mayor que cero");

        // Validación de fechaCompra (no nula o no futura)
        LocalDate fechaCompra = alimento.getFechaCompra();
        if (fechaCompra == null) {
            errores.put("fechaCompra", "La fecha de compra no puede estar vacía");
        } else {
            LocalDate hoy = LocalDate.now();
            if (fechaCompra.isAfter(hoy)) {
                errores.put("fechaCompra", "La fecha de compra no puede ser futura");
            }
        }

        // Validación de precio
        if (alimento.getPrecio() <= 0)
            errores.put("precio", "El precio debe ser mayor que cero");

        return errores;
    }
}