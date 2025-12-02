package org.vaquitas.model;

import java.time.LocalDate;

public class DTOreceta {
    private int idMedicamento;
    private int dosis;
    private LocalDate fechaInicio;
    private int idAreteGanado;
    private String padecimiento;
    private LocalDate fechaRecordatorio;

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getIdAreteGanado() {
        return idAreteGanado;
    }

    public void setIdAreteGanado(int idAreteGanado) {
        this.idAreteGanado = idAreteGanado;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public LocalDate getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    public void setFechaRecordatorio(LocalDate fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }
}
