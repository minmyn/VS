package org.vaquitas.model;

public class Consulta {
    private int idConsulta, idArete ;
    private String padecimiento ;
//    private Consulta consulta;
//    private Receta receta;
//    private Recordatorio recordatorio;
//
//    public Consulta getConsulta() {
//        return consulta;
//    }
//
//    public void setConsulta(Consulta consulta) {
//        this.consulta = consulta;
//    }
//
//    public Receta getReceta() {
//        return receta;
//    }
//
//    public void setReceta(Receta receta) {
//        this.receta = receta;
//    }
//
//    public Recordatorio getRecordatorio() {
//        return recordatorio;
//    }
//
//    public void setRecordatorio(Recordatorio recordatorio) {
//        this.recordatorio = recordatorio;
//    }

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
