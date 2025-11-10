package org.vaquitas.controller;

import io.javalin.http.Context;
import jdk.dynalink.linker.LinkerServices;
import org.vaquitas.model.Receta;
import org.vaquitas.service.RecetaService;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.List;

public class RecetaControl {
    private RecetaService recetaService;
    public RecetaControl(RecetaService recetaService) {
        this.recetaService=recetaService;
    }

    public void verRecetas(Context context){
        try {
            List<Receta> recetas = recetaService.verRecetas();
            context.status(200).json(recetas);
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}
