package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Consulta;
import org.vaquitas.util.Error;
import org.vaquitas.service.ConsultaService;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class ConsultaControl {

    private final ConsultaService consultaService;
    public ConsultaControl(ConsultaService consultaService) {
        this.consultaService=consultaService;
    }


    public void buscarConsultasPorAnimal(Context context) {
        try {
            int areteId = Integer.parseInt(context.pathParam("idArete"));
            List<Consulta> consultas = consultaService.obtenerConsultasPorArete(areteId);
            if (consultas.isEmpty()) {
                context.status(404).json(Map.of("mensaje", "No se encontraron consultas para el arete ID: " + areteId));
                return;
            }
            context.status(200).json(consultas);
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("mensaje", "El ID del arete debe ser un número entero válido (BIGINT)."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

}