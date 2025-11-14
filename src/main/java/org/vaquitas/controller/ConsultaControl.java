package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.service.ConsultaService;
import org.vaquitas.util.ConsultaValidator;
import org.vaquitas.util.RecetaValidator;
import org.vaquitas.util.RecordatorioValidator;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultaControl {

    private final ConsultaService consultaService;
    public ConsultaControl(ConsultaService consultaService) {
        this.consultaService=consultaService;
    }
}