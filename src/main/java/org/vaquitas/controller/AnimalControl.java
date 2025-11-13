package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;
import org.vaquitas.service.AnimalService;
//import org.vaquitas.util.AnimalValidator;
import org.vaquitas.util.Error;

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
//            AnimalValidator animalValidator = new AnimalValidator();
//            Map<String, String> errores = animalValidator.validarAnimal(nuevoGanado);
//            if (!errores.isEmpty()) {
//                context.status(400).json(Map.of("errores", errores));
//                return;
//            }
            animalService.registrarGanado(nuevoGanado);
            context.status(201).json("Gurdado correctamentenete");
        }catch (IllegalArgumentException e){
            context.status(404).json("Arete duplicado");
        } catch (SQLException e) {
            context.status(500).json(org.vaquitas.util.Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void visualizarGanado(Context context) throws SQLException{
        try {
            List<Animal> ganado = animalService.visualizarGanado();
            context.status(200).json(ganado);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void vizualizarGanadoActivo(Context context){
        try {
            List<Animal> ganadoActivo = animalService.visuaizarGanadoActivo();
            context.status(200).json(ganadoActivo);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void visualizarGanadoNoActivo(Context context){
        try {
            List<Animal> ganadoNoActivo = animalService.visualizarGanadoNoActivo();
            context.status(200).json(ganadoNoActivo);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void visualizarGanadoVendido(Context context){
        try {
            List<Animal> ganadoVendido = animalService.visualizarGanadoVendido();
            context.status(200).json(ganadoVendido);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void darBajaGanado(Context context) throws SQLException {
        try {
            int idArete = Integer.parseInt(context.pathParam("id"));
            Animal ganadoMuerto = context.bodyAsClass(Animal.class);
            ganadoMuerto.setIdArete(idArete);
            animalService.darBajaGanado(ganadoMuerto);
            context.status(204).json("Exitoso");
        }/*catch (NumberFormatException e){
            context.status(404).json("Ganado ineistente");
        }*/catch (IllegalArgumentException e){
            context.status(404).json("fecha invalida");
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

}