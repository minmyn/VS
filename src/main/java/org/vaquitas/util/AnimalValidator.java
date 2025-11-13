//package org.vaquitas.util;
//
//import org.vaquitas.model.Animal;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class AnimalValidator {
//    public Map<String, String> validarAnimal(Animal animal){
//        Map<String, String> errores = new HashMap<>();
//        if (animal.getFechaNacimiento() == null || animal.getNombre().isBlank())
//            errores.put("nombre", "Verifique el campo");
//        Integer n;
//        n = animal.getIdArete();
//        if (n == null || n < 0 )
//            errores.put("idArete", "Verifique el campo");
//        /*n = animal.getIdRaza();
//        if (n == null || n < 0 )
//            errores.put("idRaza", "Verifique el campo");*/
//        if (animal.getFechaNacimiento() == null)
//            errores.put("fechaNacimiento", "Verifique el campo");
//        Double m;
//        m = animal.getPeso();
//        if (m == null || m < 0)
//            errores.put("peso", "Verifique el campo");
//        if (!"Macho".equals(animal.getSexo()) && !"Hembra".equals(animal.getSexo()))
//            errores.put("sexo", "Verifique el campo");
//
//        return errores;
//    }
//}
