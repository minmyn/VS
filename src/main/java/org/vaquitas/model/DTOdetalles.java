package org.vaquitas.model;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) utilizado para devolver los detalles completos de una Receta, consolidando información de varias entidades.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class DTOdetalles {
    /** ID de arete del animal. */
    private long areteId;
    /** Nombre del animal. */
    private String nombreAnimal;
    /** Padecimiento diagnosticado en la consulta. */
    private String padecimiento;
    /** Nombre del medicamento recetado. */
    private String nombreMedicamento;
    /** Dosis prescrita. */
    private int dosis;
    /** Fecha en que inicia la administración del medicamento. */
    private LocalDate fechaInicioReceta;
    /** Fecha programada para el recordatorio de seguimiento. */
    private LocalDate fechaRecordatorio;
    /** El ID de la consulta, usado como número de receta en la vista del cliente. */
    private int numeroReceta;

    /** Obtiene el ID de la consulta (número de receta). */
    public int getNumeroReceta() {
        return numeroReceta;
    }

    /** Establece el ID de la consulta (número de receta). */
    public void setNumeroReceta(int numeroReceta) {
        this.numeroReceta = numeroReceta;
    }

    /** Obtiene el ID de arete del animal. */
    public long getAreteId() {
        return areteId;
    }

    /** Establece el ID de arete del animal. */
    public void setAreteId(long areteId) {
        this.areteId = areteId;
    }

    /** Obtiene el nombre del animal. */
    public String getNombreAnimal() {
        return nombreAnimal;
    }

    /** Establece el nombre del animal. */
    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    /** Obtiene el padecimiento. */
    public String getPadecimiento() {
        return padecimiento;
    }

    /** Establece el padecimiento. */
    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    /** Obtiene el nombre del medicamento. */
    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    /** Establece el nombre del medicamento. */
    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
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
    public LocalDate getFechaInicioReceta() {
        return fechaInicioReceta;
    }

    /** Establece la fecha de inicio de la receta. */
    public void setFechaInicioReceta(LocalDate fechaInicioReceta) {
        this.fechaInicioReceta = fechaInicioReceta;
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