package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Animal;
import org.vaquitas.service.AnimalService;

import java.sql.SQLException;
import java.util.List;

public class AnimalControl {

    private final AnimalService animalService;

    public AnimalControl(AnimalService animalService){
        this.animalService=animalService;
    }

    public void registrarGanado(Context context){
        try{
            Animal nuevoGanado = context.bodyAsClass(Animal.class);
            animalService.registrarGanado(nuevoGanado);
            context.status(201);
        }catch (SQLException e){
            context.status(500);
        }
    }

    public void visualizarGanado(Context context) throws SQLException{
        try {
            List<Animal> ganado = animalService.visualizarGanado();
            context.json(ganado);
        }catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        } catch (Exception e) {
            context.status(400);
        }
    }

    public void vizualizarGanadoActivo(Context context){
        try {
            List<Animal> ganadoActivo = animalService.visuaizarGanadoActivo();
            context.json(ganadoActivo);
            context.status(200);
        }catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        } catch (Exception e) {
            context.status(400);
        }
    }

    public void visualizarGanadoNoActivo(Context context){
        try {
            List<Animal> ganadoNoActivo = animalService.visualizarGanadoNoActivo();
            context.json(ganadoNoActivo);
            context.status(200);
        }catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        } catch (Exception e) {
            context.status(400);
        }
    }

    public void visualizarGanadoVendido(Context context){
        try {
            List<Animal> ganadoVendido = animalService.visualizarGanadoVendido();
            context.json(ganadoVendido);
            context.status(200);
        } catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        } catch (Exception e) {
            context.status(400);
        }
    }

    public void darBajaGanado(Context context) throws SQLException{
        try {
            int idArete=Integer.parseInt(context.pathParam("id"));
            Animal ganadoMuerto = context.bodyAsClass(Animal.class);
            ganadoMuerto.setIdArete(idArete);
            animalService.darBajaGanado(ganadoMuerto);
        }catch (SQLException e) {
            e.printStackTrace();
            context.status(500);
        } catch (Exception e) {
            context.status(400);
        }
    }

}