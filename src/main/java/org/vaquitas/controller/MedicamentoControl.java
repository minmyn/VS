package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Medicamento;
import org.vaquitas.service.MedicamentoService;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.List;

public class MedicamentoControl{
    private final MedicamentoService medicamentoService;
    public MedicamentoControl(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    public void registrarMedicamento(Context context){
        try {
            Medicamento nuevoMedicamento = context.bodyAsClass(Medicamento.class);
            medicamentoService.registrarMedicina(nuevoMedicamento);
            context.status(201);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void verMedicinas(Context context){
        try {
            List<Medicamento> medicamentos = medicamentoService.verMedicinas();
            context.json(medicamentos);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void actualizarMedicamento(Context context){
        try {
            int idMedicamento = Integer.parseInt(context.pathParam("id"));
            Medicamento renombrarMedicina = context.bodyAsClass(Medicamento.class);
            renombrarMedicina.setIdMedicamento(idMedicamento);
            medicamentoService.actualizarMedicamento(renombrarMedicina);
        }catch (SQLException e){
            context.status(500);
        }
    }
}