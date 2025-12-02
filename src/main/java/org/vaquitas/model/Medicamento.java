package org.vaquitas.model;

/**
 * Representa la entidad de dominio {@code Medicamento}.
 * <p>
 * Contiene información básica sobre un medicamento utilizado en la salud animal.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Medicamento {
    private int idMedicamento;
    private String nombre;
    private String descripcion;

    /**
     * Obtiene el ID (clave primaria) del medicamento.
     * @return El ID del medicamento.
     */
    public int getIdMedicamento() {
        return idMedicamento;
    }

    /**
     * Establece el ID del medicamento.
     * @param idMedicamento El nuevo ID del medicamento.
     */
    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    /**
     * Obtiene el nombre comercial o genérico del medicamento.
     * @return El nombre del medicamento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del medicamento.
     * @param nombre El nuevo nombre del medicamento.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción o uso principal del medicamento.
     * @return La descripción del medicamento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del medicamento.
     * @param descripcion La nueva descripción del medicamento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}