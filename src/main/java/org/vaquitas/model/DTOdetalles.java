package org.vaquitas.model;

import java.time.LocalDate;

public class DTOdetalles {
    // Campos del ANIMAL
    private long areteId;
    private String nombreAnimal;

    // Campos de CONSULTA
    private String padecimiento;

    // Campos de MEDICAMENTO
    private String nombreMedicamento;

    // Campos de RECETA
    private int dosis;
    private LocalDate fechaInicioReceta;

    // Campos de RECORDATORIO
    private LocalDate fechaRecordatorio;

    public long getAreteId() {
        return areteId;
    }

    public void setAreteId(long areteId) {
        this.areteId = areteId;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public void setNombreAnimal(String nombreAnimal) {
        this.nombreAnimal = nombreAnimal;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public LocalDate getFechaInicioReceta() {
        return fechaInicioReceta;
    }

    public void setFechaInicioReceta(LocalDate fechaInicioReceta) {
        this.fechaInicioReceta = fechaInicioReceta;
    }

    public LocalDate getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFechaRecordatorio(LocalDate fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }
}
