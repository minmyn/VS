package org.vaquitas.util;

import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;

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
     * <p>
     * Reglas de validación:
     * <ul>
     * <li>**nombre**: No debe ser nulo o estar en blanco.</li>
     * <li>**fechaNacimiento**: Debe ser un valor no nulo.</li>
     * <li>**idArete**: Debe ser un número positivo (mayor o igual a 1).</li>
     * <li>**raza**: El objeto {@link Raza} debe estar especificado y tener un ID positivo.</li>
     * <li>**peso**: Debe ser un valor numérico estrictamente mayor a 0.</li>
     * <li>**sexo**: Debe ser "Macho" o "Hembra" (sin distinción de mayúsculas/minúsculas).</li>
     * </ul>
     * </p>
     *
     * @param animal El objeto Animal a ser validado.
     * @return Un {@code Map<String, String>} con errores. La clave es el campo, el valor es el mensaje.
     * Retorna un mapa vacío si la validación es exitosa.
     */
    public Map<String, String> validarAnimal(Animal animal) {
        Map<String, String> errores = new HashMap<>();

        // Validación de nombre
        if (animal.getNombre() == null || animal.getNombre().isBlank())
            errores.put("nombre", "El nombre no puede estar vacío");

        // Validación de fechaNacimiento no sea nula y que no sea una fecha futura.
        if (animal.getFechaNacimiento() == null) {
            errores.put("fechaNacimiento", "La fecha de nacimiento es obligatoria");
        } else {
            LocalDate hoy = LocalDate.now();
            if (animal.getFechaNacimiento().isAfter(hoy)) {
                errores.put("fechaNacimiento", "La fecha de nacimiento no puede ser futura");
            }
        }
        // Validación de idArete (Verifica que no sea null y que sea positivo)
        if (animal.getIdArete() < 1)
            errores.put("idArete", "El número de arete debe ser positivo");

        // Validación de raza
        if (animal.getRaza() == null || animal.getRaza().getIdRaza() <= 0)
            errores.put("raza", "La raza debe estar especificada correctamente");

        // Validación de peso
        if (animal.getPeso() <= 0)
            errores.put("peso", "El peso debe ser mayor a 0");

        // Validación de sexo
        String sexo = animal.getSexo();
        if (sexo == null || !(sexo.equalsIgnoreCase("Macho") || sexo.equalsIgnoreCase("Hembra")))
            errores.put("sexo", "El sexo debe ser 'Macho' o 'Hembra'");

        return errores;
    }

    /**
     * Valida las restricciones para el registro de la fecha de baja de un {@link Animal}.
     * <p>
     * Reglas de validación:
     * <ul>
     * <li>**fechaBaja**: No debe ser nula.</li>
     * <li>La fecha de baja **no puede ser anterior a la fecha actual** (debe ser hoy o posterior).</li>
     * </ul>
     * </p>
     *
     * @param animal El objeto Animal que está siendo dado de baja (debe tener la {@code fechaBaja} ya seteada).
     * @return Un {@code Map<String, String>} con errores. La clave es el campo, el valor es el mensaje.
     * Retorna un mapa vacío si la validación es exitosa.
     */
    public Map<String, String> validarAnimalBaja(Animal animal){
        Map<String, String> errores = new HashMap<>();
        // Se valida que la fecha de baja no sea nula y que no sea una fecha futura.

        if (animal.getFechaBaja() == null) {
            errores.put("fechaBaja", "La fecha de baja es obligatoria");
        } else {
            LocalDate hoy = LocalDate.now();
            if (animal.getFechaBaja().isAfter(hoy)) {
                errores.put("fechaBaja", "La fecha de baja no puede ser futura");
            }
        }
        return errores;
    }
}