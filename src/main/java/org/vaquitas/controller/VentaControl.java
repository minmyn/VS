package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Venta;
import org.vaquitas.service.AnimalService;
import org.vaquitas.service.VentaService;

import java.sql.SQLException;
import java.util.List;

public class VentaControl {
    private final VentaService ventaService;

    public VentaControl(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    public void registrarVenta(Context context){
        try{
            int idArete = Integer.parseInt(context.pathParam("id"));
            Venta nuevaVenta = context.bodyAsClass(Venta.class);
            nuevaVenta.setIdArete(idArete);
            ventaService.registrarVenta(nuevaVenta);
            context.status(201);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void verVentas(Context context){
        try{
            List<Venta> ganadoVendido = ventaService.verVentas();
            context.status(200);
            context.json(ganadoVendido);
        }catch (SQLException e){
            context.status(500);
        }
    }
}