package org.vaquitas.model;

public class Consulta {
    private int idConsulta, idArete ;
    private String padecimiento ;

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public int getIdArete() {
        return idArete;
    }

    public void setIdArete(int idArete) {
        this.idArete = idArete;
    }

    public String getPadecimiento() {
        return padecimiento;
    }

    public void setPadecimiento(String padecimiento) {
        this.padecimiento = padecimiento;
    }
}
