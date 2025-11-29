package org.vaquitas.model;

import java.time.LocalDate;

public class DTOdetalles {
    private int areteId;
    private String nombreAnimal;
    private String padecimiento;
    private String nombreMedicamento;
    private int dosis;
    private LocalDate fechaInicioReceta;
    private LocalDate fechaRecordatorio;
    private int numeroReceta;

    public int getNumeroReceta() {
        return numeroReceta;
    }

    public void setNumeroReceta(int numeroReceta) {
        this.numeroReceta = numeroReceta;
    }

    public int getAreteId() {
        return areteId;
    }

    public void setAreteId(int areteId) {
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