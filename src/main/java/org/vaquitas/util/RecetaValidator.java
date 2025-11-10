package org.vaquitas.util;

import org.vaquitas.model.Receta;

import java.util.HashMap;
import java.util.Map;

public class RecetaValidator {
    public Map<String, String> validarReceta(Receta receta){
        Map<String, String> errores = new HashMap<>();
        Double n = receta.getDosis();
        if (n == null || n <= 0)
            errores.put("dosis", "Verifique el campo");

        return errores;
    }
}
