//package org.vaquitas.util;
//
//import org.vaquitas.model.Consulta;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ConsultaValidator {
//    public Map<String, String> validarConsulta(Consulta consulta){
//        Map<String, String> errores = new HashMap<>();
//        Integer n;
//        n = consulta.getIdArete();
//        if ( n == null || n < 0)
//            errores.put("idArete", "Verifique el campo");
//        if (consulta.getPadecimiento() == null || consulta.getPadecimiento().isBlank())
//            errores.put("padecimiento", "Verifique el campo");
//        return errores;
//    }
//}
