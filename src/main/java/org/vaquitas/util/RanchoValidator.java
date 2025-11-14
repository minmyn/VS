package org.vaquitas.util;

import org.vaquitas.model.Rancho;

import java.util.HashMap;
import java.util.Map;

public class RanchoValidator {
    public Map<String, String> validarRancho(Rancho rancho){
        Map<String, String> errores = new HashMap<>();

        if (rancho.getNombre() == null || rancho.getNombre().isBlank())
            errores.put("nombre", "Verifique el campo nombre");

        if (rancho.getUbicacion() == null || rancho.getUbicacion().isBlank())
            errores.put("ubicacion", "Verifique el campo ubicación");

        return errores;
    }
}
