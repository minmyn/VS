package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Raza;
import org.vaquitas.service.RazaService;
import org.vaquitas.util.Error;
import org.vaquitas.util.RazaValidator;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RazaControl {
    private final RazaService razaService;
    public RazaControl(RazaService razaService) {
        this.razaService=razaService;
    }

    public void registrarRaza(Context context){
        try {

            Raza nuevaRaza = context.bodyAsClass(Raza.class);
            RazaValidator razaValidator = new RazaValidator();

            Map<String, String> errores = razaValidator.validarRaza(nuevaRaza);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }

            razaService.registrarRaza(nuevaRaza);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", nuevaRaza);
            context.status(201).json(respuesta);

        }catch (IllegalArgumentException e){
            context.status(409).json(Map.of("mensaje", "Raza duplicada. " + e.getMessage()));
        }catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void verRaza(Context context){
        try {
            List<Raza> razas = razaService.verRazas();
            context.status(200).json(razas);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    // Código para RazaControl.java

    public void renombrarRaza(Context context){
        try {
            int idRaza = Integer.parseInt(context.pathParam("id"));
            Raza renombreRaza = context.bodyAsClass(Raza.class);

            // Asignar el ID de la ruta al objeto
            renombreRaza.setIdRaza(idRaza);

            // VALIDACIÓN: Renombrar solo requiere el nombre de raza, lo validamos
            RazaValidator razaValidator = new RazaValidator();
            Map<String, String> errores = razaValidator.validarRaza(renombreRaza);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }

            razaService.renombrarRaza(renombreRaza);

            // IMPLEMENTACIÓN: Estructura de respuesta solicitada (con 200 OK)
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", renombreRaza);
            context.status(200).json(respuesta);

        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("mensaje", "El ID de la raza debe ser un número entero válido."));
        } catch (IllegalArgumentException e){
            // Manejo de error para el caso de que la raza a renombrar no exista
            context.status(404).json(Map.of("mensaje", "Raza no encontrada o error de dato."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}