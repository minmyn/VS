package org.vaquitas.model;

import java.time.LocalDate;

public class Venta {
    private int idVenta;
    private int idArete;
    private double precioVenta;
    private double pesoFinal;
    private LocalDate fechaBaja;

    public int getIdArete() {
        return idArete;
    }

    public void setIdArete(int idArete) {
        this.idArete = idArete;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getPesoFinal() {
        return pesoFinal;
    }

    public void setPesoFinal(double pesoFinal) {
        this.pesoFinal = pesoFinal;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}