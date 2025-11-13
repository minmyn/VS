//package org.vaquitas.util;
//
//import org.vaquitas.model.Medicamento;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class MedicamentoValidator {
//    public Map<String, String> validarMedicamento(Medicamento medicamento){
//        Map<String, String> errores = new HashMap<>();
//        if (medicamento.getNombre() == null || medicamento.getNombre().isBlank())
//            errores.put("nombre", "Verifique el campo");
//        if (medicamento.getDescripcion() == null || medicamento.getDescripcion().isBlank())
//            errores.put("descripcion", "Verifique el campo");
//        return errores;
//    }
//}
