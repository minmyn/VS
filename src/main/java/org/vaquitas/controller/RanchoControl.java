package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Rancho;
import org.vaquitas.service.RanchoService;

import java.sql.SQLException;
import java.util.List;

public class RanchoControl {
    private final RanchoService ranchoService;
    public RanchoControl(RanchoService ranchoService) {
        this.ranchoService = ranchoService;
    }

    public void agregarRancho(Context context) throws SQLException{
        try {
            Rancho nuevoRancho = context.bodyAsClass(Rancho.class);
            ranchoService.agregarRancho(nuevoRancho);
            context.json(nuevoRancho);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void verRanchos(Context context) throws SQLException{
        try {
            List<Rancho> ranchos = ranchoService.verRanchos();
            context.json(ranchos);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void editarRancho(Context context) throws SQLException{
        try {
            int idRancho = Integer.parseInt(context.pathParam("id"));
            Rancho editarRancho = context.bodyAsClass(Rancho.class);
            editarRancho.setIdRancho(idRancho);
            ranchoService.editarRancho(editarRancho);
        }catch (SQLException e){
            context.status(500);
        }
    }

}