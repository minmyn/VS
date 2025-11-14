package org.vaquitas.util;

import org.vaquitas.model.Venta;

import java.util.HashMap;
import java.util.Map;

public class VentaValidator {
    public Map<String, String> validarVenta(Venta venta){
        Map<String, String> errores = new HashMap<>();

        if (venta.getGanado() == null || venta.getGanado().getIdArete() <= 0)
            errores.put("idArete", "El ID del arete es inválido o no está especificado.");

        if (venta.getFechaBaja() == null)
            errores.put("fechaBaja", "La fecha de baja es obligatoria.");

        Double pesoFinal = venta.getPesoFinal();
        if (pesoFinal == null || pesoFinal <= 0 )
            errores.put("pesoFinal", "El peso final debe ser mayor a 0.");

        Double precioVenta = venta.getPrecioVenta();
        if (precioVenta == null || precioVenta <= 0 )
            errores.put("precioVenta", "El precio de venta debe ser mayor a 0.");

        return errores;
    }
}
