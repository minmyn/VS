package org.vaquitas.model;

import java.time.LocalDate;

/**
 * Representa la entidad de dominio {@link Recordatorio} que establece una alarma o nota asociada a una entidad.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Recordatorio {

    /**
     * Identificador único del recordatorio (PK).
     */
    private int idRecordatorio;

    /**
     * La fecha programada en la que debe activarse el recordatorio o alerta.
     */
    private LocalDate fechaRecordatorio;

    /**
     * Obtiene el identificador único del recordatorio.
     *
     * @return El ID del recordatorio.
     */
    public int getIdRecordatorio() {
        return idRecordatorio;
    }

    /**
     * Establece el identificador único del recordatorio.
     *
     * @param idRecordatorio El nuevo ID del recordatorio.
     */
    public void setIdRecordatorio(int idRecordatorio) {
        this.idRecordatorio = idRecordatorio;
    }

    /**
     * Obtiene la fecha programada para el recordatorio.
     *
     * @return La fecha en formato {@link LocalDate}.
     */
    public LocalDate getFechaRecordatorio() {
        return fechaRecordatorio;
    }

    /**
     * Establece la fecha programada para el recordatorio.
     *
     * @param fechaRecordatorio La nueva fecha en formato {@link LocalDate}.
     */
    public void setFechaRecordatorio(LocalDate fechaRecordatorio) {
        this.fechaRecordatorio = fechaRecordatorio;
    }
}