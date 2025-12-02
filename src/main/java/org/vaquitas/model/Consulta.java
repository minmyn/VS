package org.vaquitas.model;

/**
 * Representa la entidad de dominio {@link Consulta} que contiene un registro de chequeo de salud realizado para un animal.
 * <p>
 * Una consulta está asociada a un animal y detalla el padecimiento diagnosticado.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class Consulta {
    /**
     * Identificador único de la consulta. Clave primaria (PK).
     */
    private int idConsulta;

    /**
     * Descripción del padecimiento, enfermedad o motivo de la consulta.
     */
    private String padecimiento ;

    /**
     * Objeto {@link Animal} al que se le realizó la consulta. Clave foránea (FK).
     */
    private Animal ganado;

    /**
     * Obtiene el identificador de la consulta.
     *
     * @return El ID de la consulta.
     */
    public int getIdConsulta() {
        return idConsulta;
    }

    /**
     * Establece el identificador de la consulta.
     *
     * @param idConsulta El nuevo ID de la consulta.
     */
    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    /**
     * Obtiene la descripción del padecimiento.
     *
     * @return El padecimiento.
     */
    public String getPadecimiento() {
        return padecimiento;
    }

    /**
     * Establece la descripción del padecimiento.
     *
     * @param padecimiento La nueva descripción del padecimiento.
     */
    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    /**
     * Obtiene el objeto Animal asociado a esta consulta.
     *
     * @return El objeto {@link Animal}.
     */
    public Animal getGanado() {
        return ganado;
    }

    /**
     * Establece el objeto Animal asociado a esta consulta.
     *
     * @param ganado El objeto {@link Animal} afectado.
     */
    public void setGanado(Animal ganado) {
        this.ganado = ganado;
    }
}