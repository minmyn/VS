package org.vaquitas.model;

/**
 * Representa la entidad de dominio {@link Raza} que clasifica el ganado en el rancho.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Raza {

    /**
     * Identificador único de la raza (clave primaria).
     */
    private int idRaza;

    /**
     * Nombre descriptivo de la raza del animal (e.g., "Angus", "Brahman").
     */
    private String nombreRaza;

    /**
     * Obtiene el nombre de la raza.
     *
     * @return El nombre descriptivo de la raza.
     */
    public String getNombreRaza() {
        return nombreRaza;
    }

    /**
     * Establece el nombre de la raza.
     *
     * @param nombreRaza El nuevo nombre de la raza.
     */
    public void setNombreRaza(String nombreRaza) {
        this.nombreRaza = nombreRaza;
    }

    /**
     * Obtiene el identificador único de la raza.
     *
     * @return El ID de la raza.
     */
    public int getIdRaza() {
        return idRaza;
    }

    /**
     * Establece el identificador único de la raza.
     *
     * @param idRaza El nuevo ID de la raza.
     */
    public void setIdRaza(int idRaza) {
        this.idRaza = idRaza;
    }
}