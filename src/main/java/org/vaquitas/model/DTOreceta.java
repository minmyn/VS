package org.vaquitas.model;

import java.time.LocalDate;

/**
 DTO (Data Transfer Object) utilizado para recibir los datos necesarios para la creación de una nueva Receta desde el cliente.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class DTOreceta {
    /** ID del medicamento a recetar. */
    private int idMedicamento;
    /** Dosis prescrita. */
    private int dosis;
    /** Fecha en que inicia la receta. */
    private LocalDate fechaInicio;
    /** ID de arete del animal. */
    private int idAreteGanado;
    /** Padecimiento, diagnóstico o motivo. */
    private String padecimiento;
    /** Fecha para el recordatorio de seguimiento. */
    private LocalDate fechaRecordatorio;

    /** Obtiene el ID del medicamento. */
    public int getIdMedicamento() {
        return idMedicamento;
    }

    /** Establece el ID del medicamento. */
    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    /** Obtiene la dosis. */
    public int getDosis() {
        return dosis;
    }

    /** Establece la dosis. */
    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    /** Obtiene la fecha de inicio de la receta. */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /** Establece la fecha de inicio de la receta. */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /** Obtiene el ID de arete del ganado. */
    public int getIdAreteGanado() {
        return idAreteGanado;
    }

    /** Establece el ID de arete del ganado. */
    public void setIdAreteGanado(int idAreteGanado) {
        this.idAreteGanado = idAreteGanado;
    }

    /** Obtiene el padecimiento. */
    public String getPadecimiento() {
        return padecimiento;
    }

    /** Establece el padecimiento. */
    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    /** Obtiene la fecha del recordatorio. */
    public LocalDate getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    /** Establece la fecha del recordatorio. */
    public void setFechaRecordatorio(LocalDate fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }
}