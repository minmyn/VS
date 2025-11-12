package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Medicamento;
import org.vaquitas.service.MedicamentoService;
import org.vaquitas.util.Error;
import org.vaquitas.util.MedicamentoValidator;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class MedicamentoControl{
    private final MedicamentoService medicamentoService;
    public MedicamentoControl(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    public void registrarMedicamento(Context context){
        try {
            Medicamento nuevoMedicamento = context.bodyAsClass(Medicamento.class);
            MedicamentoValidator medicamentoValidator = new MedicamentoValidator();
            Map<String, String> errores = medicamentoValidator.validarMedicamento(nuevoMedicamento);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            medicamentoService.registrarMedicina(nuevoMedicamento);
            context.status(201).json("Gurdado correctamentenete");
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void verMedicinas(Context context){
        try {
            List<Medicamento> medicamentos = medicamentoService.verMedicinas();
            context.status(200).json(medicamentos);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
    public void buscarMedicamentosPorAlgo(Context context){
        String texto = context.queryParam("nombre"); // recibe ?nombre=XXX
        try {
            List<Medicamento> medicamentos = medicamentoService.buscarMedicamentos(texto);
            context.status(200).json(medicamentos);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }


    public void actualizarMedicamento(Context context){
        try {
            int idMedicamento = Integer.parseInt(context.pathParam("id"));
            Medicamento renombrarMedicina = context.bodyAsClass(Medicamento.class);
            renombrarMedicina.setIdMedicamento(idMedicamento);
            medicamentoService.actualizarMedicamento(renombrarMedicina);
            context.status(201).json("Guardado Correctamente");
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}