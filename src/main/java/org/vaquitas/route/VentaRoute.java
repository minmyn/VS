package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.VentaControl;

public class VentaRoute {
    private final VentaControl ventaControl;
    public VentaRoute(VentaControl ventaControl) {
        this.ventaControl = ventaControl;
    }
    public void register (Javalin app){
        //app.post("/ventas", ventaControl::registrarVenta);
        app.post("/ventas/{id}", ventaControl::registrarVenta);
        app.get("/ventas", ventaControl::verVentas);
    }
}