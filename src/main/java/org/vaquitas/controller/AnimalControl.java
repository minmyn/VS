package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Animal;
import org.vaquitas.service.AnimalService;
import org.vaquitas.util.AnimalValidator;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalControl {

    private final AnimalService animalService;

    public AnimalControl(AnimalService animalService){
        this.animalService=animalService;
    }

    public void registrarGanado(Context context){

        try{
            Animal nuevoGanado = context.bodyAsClass(Animal.class);
            AnimalValidator animalValidator = new AnimalValidator();
            Map<String, String> errores = animalValidator.validarAnimal(nuevoGanado);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            animalService.registrarGanado(nuevoGanado);
            context.status(201).json(Map.of("estado", true,"data", nuevoGanado));
        } catch (IllegalArgumentException e){
            String mensaje = e.getMessage();
            if (mensaje.contains("Raza no encontrada")) {
                context.status(404).json(Map.of("mensaje", mensaje));
            } else if (mensaje.contains("Arete duplicado")) {
                context.status(409).json(Map.of("mensaje", mensaje));
            } else {
                context.status(400).json(Map.of("mensaje", mensaje));
            }
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
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

    public void verUnGanado(Context context){
        try {
            int idArete = Integer.parseInt(context.pathParam("id"));
            Animal ganado = animalService.verUnSoloGanado(idArete);

            if (ganado == null) {
                context.status(404).json(Map.of("mensaje", "Ganado con ID " + idArete + " no encontrado."));
                return;
            }
            context.status(200).json(ganado);

        } catch (NumberFormatException e){
            context.status(400).json(Map.of("mensaje", "El ID del arete debe ser un número entero válido."));
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

            AnimalValidator animalValidator = new AnimalValidator();
            Map<String, String> errores = animalValidator.validarAnimalBaja(ganadoMuerto);

            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }

            animalService.darBajaGanado(ganadoMuerto);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", ganadoMuerto);
            context.status(200).json(respuesta);

        }catch (NumberFormatException e){
            context.status(400).json(Map.of("mensaje", "El ID del arete debe ser un número entero válido."));
        }catch (IllegalArgumentException e){
            String mensajeError = e.getMessage() != null ? e.getMessage() : "Error de validación en la baja del ganado.";

            if (mensajeError.isEmpty()) {
                context.status(404).json(Map.of("mensaje", "Ganado inexistente."));
            } else {
                context.status(400).json(Map.of("mensaje", mensajeError));
            }
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

}
