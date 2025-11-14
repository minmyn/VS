package org.vaquitas.util;

import org.vaquitas.model.Alimento;

import java.util.HashMap;
import java.util.Map;

public class AlimentoValidator {

    public Map<String, String> validarAlimento(Alimento alimento) {
        Map<String, String> errores = new HashMap<>();
        if (alimento.getNombre() == null || alimento.getNombre().isBlank())
            errores.put("nombre", "El nombre no puede estar vacío");
        if (alimento.getTipo() == null || alimento.getTipo().isBlank())
            errores.put("tipo", "El tipo no puede estar vacío");
        Double cantidad = alimento.getCantidad();
        if (cantidad == null || cantidad <= 0)
            errores.put("cantidad", "La cantidad debe ser mayor que cero");
        if (alimento.getFechaCompra() == null)
            errores.put("fechaCompra", "La fecha de compra no puede estar vacía");
        Double precio = alimento.getPrecio();
        if (precio == null || precio <= 0)
            errores.put("precio", "El precio debe ser mayor que cero");
        return errores;
    }

}
