package org.vaquitas.model;

/**
 * Representa la entidad de dominio {@code Rancho}, que es la unidad geográfica
 * o administrativa donde se aloja el ganado.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Rancho {
    /**
     * Identificador único del rancho (PK). Este valor es generado
     * automáticamente por la base de datos y se utiliza para referenciar
     * esta entidad desde otras tablas (como {@code Usuario}).
     */
    private int idRancho;

    /**
     * Nombre del rancho.
     * Este campo es obligatorio y debe ser **único** en toda la base de datos
     * para evitar ambigüedades en la identificación.
     */
    private String nombre;

    /**
     * Ubicación o dirección física del rancho.
     * Detalla la localización del rancho, lo que facilita la logística.
     */
    private String ubicacion;

    /**
     * Obtiene el ID del rancho.
     *
     * @return El ID único del rancho.
     */
    public int getIdRancho() {
        return idRancho;
    }

    /**
     * Establece el ID del rancho.
     *
     * @param idRancho El nuevo ID del rancho.
     */
    public void setIdRancho(int idRancho) {
        this.idRancho = idRancho;
    }

    /**
     * Obtiene el nombre del rancho.
     *
     * @return El nombre del rancho.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del rancho.
     *
     * @param nombre El nuevo nombre del rancho.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la ubicación del rancho.
     *
     * @return La ubicación (dirección) del rancho.
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Establece la ubicación del rancho.
     *
     * @param ubicacion La nueva ubicación del rancho.
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}