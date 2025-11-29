package org.vaquitas.model;

public class Reporte {

    private String columna;
    private double numero;
    private double porcentaje;

    public String getColumna() {return columna;}

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public double getNumero() {
        return numero;
    }

    public void setNumero(double numero) {
        this.numero = numero;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
}
