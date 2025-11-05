package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Alimento;
import org.vaquitas.service.AlimentoService;

import java.sql.SQLException;
import java.util.List;

public class AlimentoControl {
    private final AlimentoService alimentoService;
    public AlimentoControl(AlimentoService alimentoService) {
        this.alimentoService=alimentoService;
    }

    public void guardarAlimentos(Context context) {
        try{
            Alimento nuevoAlimento = context.bodyAsClass(Alimento.class);
            alimentoService.guardarAlimentos(nuevoAlimento);
            context.status(201);
        }catch (SQLException e) {
            context.status(500);
        }
    }

    public void verAlimentos(Context context){
        try{
            List<Alimento> alimentos = alimentoService.verAlimentos();
            context.json(alimentos);
        }catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        } catch (Exception e) {
            context.status(400);
        }
    }

    public void  editarAlimentos(Context context){
        try {
            int idAlimento = Integer.parseInt(context.pathParam("id"));
            Alimento editarAlimento = context.bodyAsClass(Alimento.class);
            editarAlimento.setIdCompra(idAlimento);
            alimentoService.editarAlimentos(editarAlimento);
        }catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        } catch (Exception e) {
            context.status(400);
        }
    }
}
