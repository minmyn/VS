package org.vaquitas.model;

import java.time.LocalDate;

/**
 * Representa la entidad de dominio {@link Alimento} que contiene la compra de un artículo de alimento o insumo dentro de un rancho.
 * <p>
 * Esta entidad se utiliza para registrar y rastrear el inventario y los costos
 * asociados a la alimentación del ganado.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Alimento {
    /**
     * Identificador único de la compra del alimento.
     * Es la clave primaria para la tabla de registro de compras de alimento.
     */
    private int idCompra;

    /**
     * Cantidad del alimento comprado, en la unidad de medida correspondiente (por ejemplo, kilogramos).
     */
    private double cantidad;

    /**
     * Precio total de la compra del alimento (costo total).
     */
    private double precio;

    /**
     * Categoría o tipo del alimento (Concentrado, Balanceado, Suplemento y Forraje).
     */
    private String tipo;

    /**
     * Nombre específico del producto (por ejemplo, "Alfalfa", "Pastura", "Heno").
     */
    private String nombre;

    /**
     * Referencia al rancho que realizó la compra del alimento.
     * Es la clave foránea que relaciona esta compra con la entidad {@link Rancho}.
     */
    private Rancho idRancho;

    /**
     * Fecha en la que se realizó la compra del alimento.
     */
    private LocalDate fechaCompra;

    // --- MÉTODOS GETTERS Y SETTERS ---

    /**
     * Obtiene el identificador único de la compra.
     *
     * @return el identificador de la compra de alimento
     */
    public int getIdCompra() {
        return idCompra;
    }

    /**
     * Establece el identificador único de la compra.
     *
     * @param idCompra el nuevo identificador de la compra de alimento
     */
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    /**
     * Obtiene la cantidad comprada del alimento.
     *
     * @return la cantidad de alimento comprado
     */
    public double getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad comprada del alimento.
     *
     * @param cantidad la nueva cantidad de alimento; se espera un valor positivo
     */
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio (costo total) de la compra.
     *
     * @return el precio registrado de la compra
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio (costo total o unitario) de la compra.
     *
     * @param precio el nuevo precio registrado; se espera un valor no negativo
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el tipo del alimento.
     *
     * @return el tipo de alimento como {@code String}
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece la categoría o tipo del alimento.
     *
     * @param tipo el nuevo tipo de alimento
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre específico del producto.
     *
     * @return el nombre o marca del alimento
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre específico del producto.
     *
     * @param nombre el nuevo nombre del alimento
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la fecha en la que se registró la compra.
     *
     * @return la fecha de la compra como un objeto {@link LocalDate}
     */
    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    /**
     * Establece la fecha de la compra.
     *
     * @param fechaCompra la nueva fecha de la compra
     */
    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    /**
     * Obtiene la referencia al rancho que compró el alimento.
     *
     * @return el objeto {@link Rancho} asociado a la compra
     */
    public Rancho getIdRancho() {
        return idRancho;
    }

    /**
     * Establece la referencia al rancho.
     *
     * @param idRancho el objeto {@link Rancho} al que pertenece la compra
     */
    public void setIdRancho(Rancho idRancho) {
        this.idRancho = idRancho;
    }
}
