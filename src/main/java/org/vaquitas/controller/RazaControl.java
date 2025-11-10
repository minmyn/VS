package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Raza;
import org.vaquitas.service.RazaService;
import org.vaquitas.util.Error;
import org.vaquitas.util.RazaValidator;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RazaControl {
    private final RazaService razaService;
    public RazaControl(RazaService razaService) {
        this.razaService=razaService;
    }

    public void registrarRaza(Context context) throws SQLClientInfoException{
        try {
            Raza nuevaRaza = context.bodyAsClass(Raza.class);
            RazaValidator razaValidator = new RazaValidator();
            Map<String, String> errores = razaValidator.validarRaza(nuevaRaza);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            razaService.registrarRaza(nuevaRaza);
            context.status(201).json("Gurdado correctamentenete");
        }catch (IllegalArgumentException e){
            context.status(404).json("Raza duplicada");
        }catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void verRaza(Context context) throws SQLClientInfoException{
        try {
            List<Raza> razas = razaService.verRazas();
            context.status(200).json(razas);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void renombrarRaza(Context context) throws SQLClientInfoException{
        try {
            int idRaza = Integer.parseInt(context.pathParam("id"));
            Raza renombreRaza = context.bodyAsClass(Raza.class);
            renombreRaza.setIdRaza(idRaza);
            razaService.renombrarRaza(renombreRaza);
            context.status(201).json("Exitoso");
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}