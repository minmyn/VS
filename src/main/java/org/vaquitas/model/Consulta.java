package org.vaquitas.model;

public class Consulta {
    private int idConsulta; // Se mantiene para el findAll
    private String padecimiento ; // Viene del JSON
    private Animal ganado;

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }

    public Animal getGanado() {
        return ganado;
    }

    public void setGanado(Animal ganado) {
        this.ganado = ganado;
    }


}
