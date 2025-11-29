package org.vaquitas.util;

import org.vaquitas.model.Animal;

import java.time.LocalDate;
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
        if (arete == null || arete < 1)
            errores.put("idArete", "El número de arete debe ser positivo");

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
        LocalDate hoy = LocalDate.now();
        if (animal.getFechaBaja() == null || animal.getFechaBaja().isBefore(hoy))
            errores.put("fechaBaja", "Verificar fecha");
        return errores;
    }
}
