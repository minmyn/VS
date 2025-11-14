package org.vaquitas.util;

import org.vaquitas.model.Animal;

import java.util.HashMap;
import java.util.Map;

public class AnimalValidator {
    public Map<String, String> validarAnimal(Animal animal) {
        Map<String, String> errores = new HashMap<>();

        if (animal.getNombre() == null || animal.getNombre().isBlank())
            errores.put("nombre", "El nombre no puede estar vacío");

        if (animal.getFechaNacimiento() == null)
            errores.put("fechaNacimiento", "La fecha de nacimiento es obligatoria");

        Integer arete = animal.getIdArete();
        if (arete == null || arete < 0)
            errores.put("idArete", "El número de arete debe ser positivo");

        // Validación de raza (asegúrate de que el objeto Raza no sea null y su ID válido)
        if (animal.getRaza() == null || animal.getRaza().getIdRaza() <= 0)
            errores.put("raza", "La raza debe estar especificada correctamente");

        Double peso = animal.getPeso();
        if (peso == null || peso <= 0)
            errores.put("peso", "El peso debe ser mayor a 0");

        String sexo = animal.getSexo();
        if (sexo == null || !(sexo.equalsIgnoreCase("Macho") || sexo.equalsIgnoreCase("Hembra")))
            errores.put("sexo", "El sexo debe ser 'Macho' o 'Hembra'");

        return errores;
    }

    public Map<String, String> validarAnimalBaja(Animal animal){
        Map<String, String> errores = new HashMap<>();
        if (animal.getFechaBaja() == null)
            errores.put("fechaBaja", "La fecha de baja es obligatoria");
        return errores;
    }
}
