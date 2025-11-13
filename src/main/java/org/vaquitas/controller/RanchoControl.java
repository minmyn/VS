package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Rancho;
import org.vaquitas.service.RanchoService;
import org.vaquitas.util.Error;
//import org.vaquitas.util.RanchoValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RanchoControl {
    private final RanchoService ranchoService;
    public RanchoControl(RanchoService ranchoService) {
        this.ranchoService = ranchoService;
    }

    public void agregarRancho(Context context){
        try {
            Rancho nuevoRancho = context.bodyAsClass(Rancho.class);
//            RanchoValidator ranchoValidator = new RanchoValidator();
//            Map<String, String> errores = ranchoValidator.validarRancho(nuevoRancho);
//            if (!errores.isEmpty()) {
//                context.status(400).json(Map.of("errores", errores));
//                return;
//            }
            ranchoService.agregarRancho(nuevoRancho);
            context.status(201).json("Gurdado correctamentenete");
        }catch (IllegalArgumentException e){
            context.status(404).json("nombre duplicado");
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void verRanchos(Context context){
        try {
            List<Rancho> ranchos = ranchoService.verRanchos();
            context.status(200).json(ranchos);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void editarRancho(Context context){
        try {
            int idRancho = Integer.parseInt(context.pathParam("id"));
            Rancho editarRancho = context.bodyAsClass(Rancho.class);
            editarRancho.setIdRancho(idRancho);
            ranchoService.editarRancho(editarRancho);
            context.status(201).json("Exitoso");
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

}