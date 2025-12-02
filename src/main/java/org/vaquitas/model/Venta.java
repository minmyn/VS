package org.vaquitas.model;

import java.time.LocalDate;

/**
 * Representa la entidad de dominio {@code Venta}, que registra la baja de un animal por venta.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Venta {
    /**
     * Identificador único de la venta (PK).
     */
    private int idVenta;

    /**
     * Precio final de la transacción de venta del animal.
     */
    private double precioVenta;

    /**
     * Peso registrado del animal al momento de la venta.
     */
    private double pesoFinal;

    /**
     * Fecha en la que se registra la venta y, por ende, la baja del animal del hato.
     */
    private LocalDate fechaBaja;

    /**
     * El objeto {@link Animal} asociado a esta venta. Contiene el ID del arete (FK).
     */
    private Animal ganado;

    /**
     * Obtiene el objeto {@link Animal} que fue vendido.
     * @return El animal asociado a la venta.
     */
    public Animal getGanado() {return ganado;}

    /**
     * Establece el animal que está siendo vendido.
     * @param ganado El objeto {@link Animal}.
     */
    public void setGanado(Animal ganado) {
        this.ganado = ganado;
    }

    /**
     * Obtiene el ID (clave primaria) de la venta.
     * @return El ID de la venta.
     */
    public int getIdVenta() {
        return idVenta;
    }

    /**
     * Establece el ID de la venta.
     * @param idVenta El nuevo ID de la venta.
     */
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    /**
     * Obtiene el precio final al que se vendió el animal.
     * @return El precio de venta.
     */
    public double getPrecioVenta() {
        return precioVenta;
    }

    /**
     * Establece el precio final de la venta.
     * @param precioVenta El nuevo precio de venta.
     */
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    /**
     * Obtiene el peso registrado del animal en el momento de la venta.
     * @return El peso final del animal.
     */
    public double getPesoFinal() {
        return pesoFinal;
    }

    /**
     * Establece el peso registrado del animal en la venta.
     * @param pesoFinal El nuevo peso final.
     */
    public void setPesoFinal(double pesoFinal) {
        this.pesoFinal = pesoFinal;
    }

    /**
     * Obtiene la fecha en la que se realizó la venta (fecha de baja del animal).
     * @return La fecha de baja.
     */
    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    /**
     * Establece la fecha de la venta.
     * @param fechaBaja La nueva fecha de baja.
     */
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}