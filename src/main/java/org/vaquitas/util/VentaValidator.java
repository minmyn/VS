//package org.vaquitas.util;
//
//import org.vaquitas.model.Venta;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class VentaValidator {
//    public Map<String, String> validarVenta(Venta venta){
//        Map<String, String> errores = new HashMap<>();
//        Integer m = venta.getIdArete();
//        if (m== null || m < 1)
//            errores.put("idArete", "Verifique el campo");
//        if (venta.getFechaBaja() == null)
//            errores.put("fechaBaja", "Verifique el campo");
//        Double n;
//        n = venta.getPesoFinal();
//        if (n == null || n <= 0 )
//            errores.put("pesoFinal", "Verifique el campo");
//        n = venta.getPrecioVenta();
//        if (n == null || n <= 0 )
//            errores.put("precio","Verifique el campo");
//        return errores;
//    }
//}
