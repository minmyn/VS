//package org.vaquitas.util;
//
//import org.vaquitas.model.Alimento;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class AlimentoValidator {
//    public Map<String, String> validarAlimento(Alimento alimento) {
//        Map<String, String> errores = new HashMap<>();
//        if (alimento.getNombre() == null || alimento.getNombre().isBlank())
//            errores.put("nombre", "El nombre no puede estar vacío");
//        if (alimento.getTipo() == null || alimento.getTipo().isBlank())
//            errores.put("tipo", "El tipo no puede estar vacío");
//        Double n;
//        n = alimento.getCantidad();
//         if (n == null || n <= 0)
//            errores.put("cantidad", "Verifique precio");
//        if (alimento.getFechaCompra() == null)
//            errores.put("fechaCompra", "No puede haber campos vacios");
//        n = alimento.getPrecio();
//        if (n == null || n <= 0)
//            errores.put("precio", "Verifique precio");
//        return errores;
//    }
//
//    public Alimento alimentoActualizar(Alimento alimentoOriginal, Alimento alimentoActualizar, int idCompra){
//        if (alimentoActualizar.getTipo()== null)
//            alimentoActualizar.setTipo(alimentoOriginal.getTipo());
//        if (alimentoActualizar.getNombre()==null)
//            alimentoActualizar.setNombre(alimentoOriginal.getNombre());
//        Double n;
//        n = alimentoActualizar.getCantidad();
//        if (n == null || n <0)
//            alimentoActualizar.setCantidad(alimentoOriginal.getCantidad());
//        n = alimentoActualizar.getPrecio();
//        if (n == null || n<0)
//            alimentoActualizar.setPrecio(alimentoOriginal.getPrecio());
//        if (alimentoActualizar.getFechaCompra() == null)
//            alimentoActualizar.setFechaCompra(alimentoOriginal.getFechaCompra());
//        alimentoActualizar.setIdCompra(idCompra);
//        return alimentoActualizar;
//    }
//
//}
