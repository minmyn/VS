package org.vaquitas.model;
/**
 * Representa la entidad de dominio {@link Reporte} que contiene un informe o resumen de datos generado por el sistema.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Reporte {

    /**
     * El nombre o identificador de la columna a la que pertenecen los datos del reporte.
     */
    private String columna;

    /**
     * Un valor numérico principal asociado a la columna.
     * Puede representar una cantidad, un monto total, un conteo o cualquier otro dato numerico, dependiendo el reporte.
     */
    private double numero;

    /**
     * Un valor de porcentaje asociado a la columna.
     * Representa la proporción de {@code numero} respecto a un total, o una tasa de crecimiento/cambio.
     */
    private double porcentaje;

    /**
     * Una nota o comentario adicional que ofrece contexto sobre los datos del reporte.
     * Puede contener advertencias o resúmenes textuales.
     */
    private String nota;

    /**
     * Obtiene el nombre o identificador de la columna/categoría de este reporte (e.g., "Sexo", "Estado").
     *
     * @return El nombre de la columna (String).
     */
    public String getColumna() {
        return columna;
    }

    /**
     * Establece el nombre o identificador de la columna/categoría de este reporte.
     *
     * @param columna El nombre de la columna (String).
     */
    public void setColumna(String columna) {
        this.columna = columna;
    }

    /**
     * Obtiene el valor numérico principal del reporte.
     *
     * @return El valor numérico (double).
     */
    public double getNumero() {
        return numero;
    }

    /**
     * Establece el valor numérico principal del reporte.
     *
     * @param numero El valor numérico (double).
     */
    public void setNumero(double numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el valor de porcentaje asociado al reporte.
     *
     * @return El valor de porcentaje (double).
     */
    public double getPorcentaje() {
        return porcentaje;
    }

    /**
     * Establece el valor de porcentaje asociado al reporte.
     *
     * @param porcentaje El valor de porcentaje (double).
     */
    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    /**
     * Obtiene la nota o comentario adicional del reporte.
     *
     * @return La nota o comentario (String).
     */
    public String getNota() {
        return nota;
    }

    /**
     * Establece la nota o comentario adicional del reporte.
     *
     * @param nota La nota o comentario (String).
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
}