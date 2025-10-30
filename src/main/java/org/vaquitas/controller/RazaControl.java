package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Raza;
import org.vaquitas.service.RazaService;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.List;

public class RazaControl {
    private final RazaService razaService;
    public RazaControl(RazaService razaService) {
        this.razaService=razaService;
    }

    public void registrarRaza(Context context) throws SQLClientInfoException{
        try {
            Raza nuevaRaza = context.bodyAsClass(Raza.class);
            razaService.registrarRaza(nuevaRaza);
            context.status(201);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void verRaza(Context context) throws SQLClientInfoException{
        try {
            List<Raza> razas = razaService.verRazas();
            context.json(razas);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void renombrarRaza(Context context) throws SQLClientInfoException{
        try {
            int idRaza = Integer.parseInt(context.pathParam("id"));
            Raza renombreRaza = context.bodyAsClass(Raza.class);
            renombreRaza.setIdRaza(idRaza);
            razaService.renombrarRaza(renombreRaza);
        }catch (SQLException e){
            context.status(500);
        }
    }
}