package org.vaquitas.model;

import java.time.LocalDate;

/**
 * Clase de modelo que representa un **Animal** individual dentro del inventario del rancho.
 * <p>
 * Esta entidad contiene los datos básicos de identificación, genéticos y de estatus
 * de cada ejemplar bovino.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Animal {
    /**
     * Identificador único del animal, generalmente el número de arete (etiqueta de identificación).
     * Es la clave primaria (PK).
     */
    private int idArete;

    /**
     * Objeto que representa la raza del animal.
     * Es una clave foránea que apunta a la entidad {@link Raza}.
     */
    private Raza raza;

    /**
     * Nombre de identificación amigable o común del animal. Puede ser opcional.
     */
    private String nombre;

    /**
     * Fecha exacta de nacimiento del animal.
     */
    private LocalDate fechaNacimiento;

    /**
     * Peso actual del animal, registrado en la unidad de medida estándar del rancho (e.g., kilogramos).
     */
    private double peso;

    /**
     * Sexo del animal. Debe ser 'Macho' o 'Hembra'.
     */
    private String sexo;

    /**
     * Estatus actual del animal (e.g., "Activo", "Vendido", "Muerto").
     * Determina si el animal está actualmente en el inventario.
     */
    private String estatus;

    /**
     * Fecha en la que el animal salió del inventario (Venta o Muerte).
     * Es nula si el animal está en estatus 'Activo'.
     */
    private LocalDate fechaBaja;

    /**
     * Referencia al rancho al que pertenece el animal.
     * Es una clave foránea que apunta a la entidad {@link Rancho}.
     */
    private Rancho rancho;

    // --- MÉTODOS GETTERS Y SETTERS ---

    /**
     * Obtiene el objeto de la raza del animal.
     *
     * @return El objeto {@link Raza} asociado.
     */
    public Raza getRaza() {
        return raza;
    }

    /**
     * Establece la raza del animal.
     *
     * @param raza El nuevo objeto {@link Raza}.
     */
    public void setRaza(Raza raza) {
        this.raza = raza;
    }

    /**
     * Obtiene el identificador de arete.
     *
     * @return El número de arete.
     */
    public int getIdArete() {
        return idArete;
    }

    /**
     * Establece el identificador de arete.
     *
     * @param idArete El nuevo número de arete. Debe ser un valor positivo.
     */
    public void setIdArete(int idArete) {
        this.idArete = idArete;
    }

    /**
     * Obtiene el nombre amigable del animal.
     *
     * @return El nombre del animal.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre amigable del animal.
     *
     * @param nombre El nuevo nombre del animal.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la fecha de nacimiento.
     *
     * @return La fecha de nacimiento como {@link LocalDate}.
     */
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Establece la fecha de nacimiento.
     *
     * @param fechaNacimiento La nueva fecha de nacimiento.
     */
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Obtiene el peso del animal.
     *
     * @return El peso del animal.
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Establece el peso actual.
     *
     * @param peso El nuevo peso. Debe ser mayor a cero.
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }

    /**
     * Obtiene el sexo del animal.
     *
     * @return El sexo (e.g., "Macho" o "Hembra").
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Establece el sexo del animal.
     *
     * @param sexo El nuevo sexo.
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * Obtiene la fecha de baja del animal.
     *
     * @return La fecha de baja, o {@code null} si el animal está activo.
     */
    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    /**
     * Establece la fecha de baja del animal.
     *
     * @param fechaBaja La nueva fecha de baja.
     */
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    /**
     * Obtiene el estatus actual del animal.
     *
     * @return El estatus (e.g., "Activo", "Vendido", "Muerto").
     */
    public String getEstatus() {
        return estatus;
    }

    /**
     * Establece el estatus actual del animal.
     *
     * @param estatus El nuevo estatus.
     */
    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}