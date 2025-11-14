package org.vaquitas.model;

import java.time.LocalDate;

public class Venta {
    private int idVenta;
    private double precioVenta;
    private double pesoFinal;
    private LocalDate fechaBaja;
    private Animal ganado;

    public Animal getGanado() {
        return ganado;
    }

    public void setGanado(Animal ganado) {
        this.ganado = ganado;
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



