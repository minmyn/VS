package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.ConsultaRequest;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.service.ConsultaService;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaControl {

    private final ConsultaService consultaService;
    public ConsultaControl(ConsultaService consultaService) {
        this.consultaService=consultaService;
    }

    public void crearConsulta(Context context){
        try {
            ConsultaRequest consultaRequest = context.bodyAsClass(ConsultaRequest.class);
            Consulta consulta = consultaRequest.getConsulta();
            Receta receta = consultaRequest.getReceta();
            Recordatorio recordatorio = consultaRequest.getRecordatorio();
            consultaService.registrarConsulta(consulta, receta, recordatorio);
            context.status(201);
        }catch (SQLException e){
            e.printStackTrace();
            context.status(500);
        }
    }

    public void verConsultas(Context context){
        try {
            List<Consulta> consultas = consultaService.verConsultas();
            context.json(consultas);
        }catch (SQLException e){
            context.status(500);
        }
    }
}
