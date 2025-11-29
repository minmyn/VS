package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Animal;
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
            int idArete = Integer.parseInt(context.pathParam("id"));
            Venta nuevaVenta = context.bodyAsClass(Venta.class);
            Animal animal = new Animal();
            animal.setIdArete(idArete);
            nuevaVenta.setGanado(animal);
            VentaValidator ventaValidator = new VentaValidator();
            Map<String,String> errores = ventaValidator.validarVenta(nuevaVenta);
            if (!errores.isEmpty()){
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            ventaService.registrarVenta(nuevaVenta);
            context.status(201).json(Map.of("estado", true, "mensaje", "Venta registrada con éxito", "data", nuevaVenta));
        }catch (NumberFormatException e){
            context.status(400).json(Map.of("mensaje", "El ID del arete debe ser un número entero válido."));
        } catch (IllegalArgumentException e){
            context.status(404).json(Map.of("mensaje", e.getMessage()));
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
}