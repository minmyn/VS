package org.vaquitas.model;

import java.time.LocalDate;

/**
 * Representa la entidad de dominio {@link Receta} que describe un tratamiento médico o prescripción aplicado a un animal.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Receta {

    /**
     * El medicamento prescrito. Es una clave foránea a la entidad {@link Medicamento}.
     */
    private Medicamento medicamento;

    /**
     * El recordatorio de seguimiento asociado a esta receta (e.g., próxima dosis, fecha de finalización).
     * Es una clave foránea a la entidad {@link Recordatorio}.
     */
    private Recordatorio recordatorio;

    /**
     * La consulta médica a la que pertenece esta receta.
     * Es una clave foránea a la entidad {@link Consulta}.
     */
    private Consulta consulta;

    /**
     * La dosis o cantidad del medicamento administrado o prescrito (e.g., mililitros, unidades).
     */
    private int dosis;

    /**
     * La fecha en la que se inició el tratamiento o se aplicó el medicamento.
     */
    private LocalDate fechaInicio;

    /**
     * Obtiene el medicamento prescrito.
     *
     * @return El objeto {@link Medicamento}.
     */
    public Medicamento getMedicamento() {
        return medicamento;
    }

    /**
     * Establece el medicamento prescrito.
     *
     * @param medicamento El objeto {@link Medicamento}.
     */
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    /**
     * Obtiene el recordatorio asociado a la receta.
     *
     * @return El objeto {@link Recordatorio}.
     */
    public Recordatorio getRecordatorio() {
        return recordatorio;
    }

    /**
     * Establece el recordatorio asociado a la receta.
     *
     * @param recordatorio El objeto {@link Recordatorio}.
     */
    public void setRecordatorio(Recordatorio recordatorio) {
        this.recordatorio = recordatorio;
    }

    /**
     * Obtiene la consulta a la que pertenece esta receta.
     *
     * @return El objeto {@link Consulta}.
     */
    public Consulta getConsulta() {
        return consulta;
    }

    /**
     * Establece la consulta a la que pertenece esta receta.
     *
     * @param consulta El objeto {@link Consulta}.
     */
    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    /**
     * Obtiene la dosis o cantidad del medicamento.
     *
     * @return La dosis en unidades enteras.
     */
    public int getDosis() {
        return dosis;
    }

    /**
     * Establece la dosis o cantidad del medicamento.
     *
     * @param dosis La nueva dosis.
     */
    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    /**
     * Obtiene la fecha de inicio del tratamiento.
     *
     * @return La fecha de inicio en formato {@link LocalDate}.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del tratamiento.
     *
     * @param fechaInicio La nueva fecha de inicio.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
}