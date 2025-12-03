package org.vaquitas.util;

import org.vaquitas.model.Animal;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de utilidad que implementa la lógica de validación para la entidad {@link Animal}.
 * <p>
 * Contiene métodos para validar la creación y las actualizaciones de estatus de los animales.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AnimalValidator {

    /**
     * Valida los campos obligatorios para el registro inicial de un objeto {@link Animal}.
     *
     * @param animal El objeto Animal a ser validado.
     * @return Un {@code Map<String, String>} con errores. Retorna un mapa vacío si la validación es exitosa.
     */
    public Map<String, String> validarAnimal(Animal animal) {
        Map<String, String> errores = new HashMap<>();

        if (animal.getNombre() == null || animal.getNombre().isBlank())
            errores.put("nombre", "El nombre no puede estar vacío");

        if (animal.getFechaNacimiento() == null) {
            errores.put("fechaNacimiento", "La fecha de nacimiento es obligatoria");
        } else if (animal.getFechaNacimiento().isAfter(LocalDate.now())) {
            errores.put("fechaNacimiento", "La fecha de nacimiento no puede ser futura");
        }

        if (animal.getIdArete() < 1)
            errores.put("idArete", "El número de arete debe ser positivo");

        if (animal.getRaza() == null || animal.getRaza().getIdRaza() <= 0)
            errores.put("raza", "La raza debe estar especificada correctamente");

        if (animal.getPeso() <= 0)
            errores.put("peso", "El peso debe ser mayor a 0");

        String sexo = animal.getSexo();
        if (sexo == null || !(sexo.equalsIgnoreCase("Macho") || sexo.equalsIgnoreCase("Hembra")))
            errores.put("sexo", "El sexo debe ser 'Macho' o 'Hembra'");

        return errores;
    }

    /**
     * Valida las restricciones para el registro de la fecha de baja de un {@link Animal}.
     *
     * @param animal El objeto Animal que está siendo dado de baja.
     * @return Un {@code Map<String, String>} con errores. Retorna un mapa vacío si la validación es exitosa.
     */
    public Map<String, String> validarAnimalBaja(Animal animal){
        Map<String, String> errores = new HashMap<>();

        if (animal.getFechaBaja() == null) {
            errores.put("fechaBaja", "La fecha de baja es obligatoria");
        } else if (animal.getFechaBaja().isAfter(LocalDate.now())) {
            errores.put("fechaBaja", "La fecha de baja no puede ser futura");
        }
        return errores;
    }
}