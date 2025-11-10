package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Venta;
import org.vaquitas.service.VentaService;
import org.vaquitas.util.Error;
import org.vaquitas.util.VentaValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class VentaControl {
    private final VentaService ventaService;

    public VentaControl(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public void registrarVenta(Context context){
        try{
            Venta nuevaVenta = context.bodyAsClass(Venta.class);
            VentaValidator ventaValidator = new VentaValidator();
            Map<String,String> errores = ventaValidator.validarVenta(nuevaVenta);
            if (!errores.isEmpty()){
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            if (ventaService.encontrarVacaVendida(nuevaVenta.getIdArete())){
                context.status(404).json("No se puede vender dos veces la misma vaca");
                return;
            }else if(!ventaService.encontrarVaca(nuevaVenta.getIdArete())){
                context.status(404).json("Ganado inexistente");
                return;
            }
            ventaService.registrarVenta(nuevaVenta);
            context.status(201).json("Exitoso");
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void verVentas(Context context){
        try{
            List<Venta> ganadoVendido = ventaService.verVentas();
            context.status(200).json(ganadoVendido);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

//    public void editarVenta(Context context){
//        try{
//            int idVenta = Integer.parseInt(context.pathParam("id"));
//            Venta editarVenta = context.bodyAsClass(Venta.class);
//            editarVenta.setIdArete(idVenta);
//            context.status(201).json("Exitoso");
//        } catch (SQLException e) {
//            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
//        } catch (Exception e) {
//            context.status(500).json(Error.getApiServiceError());
//        }
//    }
}