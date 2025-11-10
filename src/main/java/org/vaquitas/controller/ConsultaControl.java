package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.ConsultaRequest;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.service.ConsultaService;
import org.vaquitas.util.ConsultaValidator;
import org.vaquitas.util.Error;
import org.vaquitas.util.RecetaValidator;
import org.vaquitas.util.RecordatorioValidator;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultaControl {

    private final ConsultaService consultaService;
    public ConsultaControl(ConsultaService consultaService) {
        this.consultaService=consultaService;
    }

    public void crearConsulta(Context context){
        try {
            ConsultaRequest consultaRequest = context.bodyAsClass(ConsultaRequest.class);
            Consulta consulta = consultaRequest.getConsulta();
            ConsultaValidator consultaValidator = new ConsultaValidator();
            Map<String, String> erroresConsulta = consultaValidator.validarConsulta(consulta);
            Receta receta = consultaRequest.getReceta();
            RecetaValidator recetaValidator = new RecetaValidator();
            Map<String, String> erroresReceta = recetaValidator.validarReceta(receta);
            Recordatorio recordatorio = consultaRequest.getRecordatorio();
            RecordatorioValidator recordatorioValidator = new RecordatorioValidator();
            Map<String, String> erroresRecordatorio = recordatorioValidator.validarRecordatorio(recordatorio);
            if (!erroresConsulta.isEmpty() || !erroresReceta.isEmpty() || !erroresRecordatorio.isEmpty()){
                Map<String, String> errores = new HashMap<>(erroresConsulta);
                errores.putAll(erroresReceta);
                errores.putAll(erroresRecordatorio);
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            consultaService.registrarConsulta(consulta, receta, recordatorio);
            context.status(201).json("Gurdado correctamentenete");
        } catch (IllegalArgumentException e){
            context.status(404).json("Arete no encontrado");
        }catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void verConsultas(Context context){
        try {
            List<Consulta> consultas = consultaService.verConsultas();
            context.status(200).json(consultas);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}
