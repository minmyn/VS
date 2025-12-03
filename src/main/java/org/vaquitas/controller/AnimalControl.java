package org.vaquitas.controller;

import io.javalin.http.Context;
import org.vaquitas.model.Animal;
import org.vaquitas.service.AnimalService;
import org.vaquitas.util.AnimalValidator;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de la entidad {@link Animal} (Ganado).
 * <p>
 * Es responsable de recibir peticiones HTTP, realizar validaciones, manejar errores
 * de negocio (4xx) y coordinar con la capa de servicio ({@link AnimalService}).
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AnimalControl {

    private final AnimalService animalService;

    /**
     * Constructor que inyecta la dependencia del servicio.
     *
     * @param animalService El servicio que contiene la lógica de negocio.
     */
    public AnimalControl(AnimalService animalService){
        this.animalService=animalService;
    }

    /**
     * Registra un nuevo animal en la base de datos.
     * <p>Procesa la petición POST a {@code /ganado}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void registrarGanado(Context context){
        try{
            Animal nuevoGanado = context.bodyAsClass(Animal.class);
            AnimalValidator animalValidator = new AnimalValidator();

            Map<String, String> errores = animalValidator.validarAnimal(nuevoGanado);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            animalService.registrarGanado(nuevoGanado);

            context.status(201).json(Map.of("estado", true, "mensaje", "Ganado registrado con éxito", "data", nuevoGanado));
        } catch (IllegalArgumentException e){
            String mensaje = e.getMessage();
            if (mensaje != null && mensaje.contains("Raza no encontrada")) {
                context.status(404).json(Map.of("estado", false, "mensaje", mensaje));
            } else if (mensaje != null && mensaje.contains("Arete duplicado")) {
                context.status(409).json(Map.of("estado", false, "mensaje", mensaje));
            } else {
                context.status(400).json(Map.of("estado", false, "mensaje", mensaje));
            }
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera y visualiza todos los animales (activos e inactivos) registrados.
     * <p>Procesa la petición GET a {@code /ganado}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void visualizarGanado(Context context){
        try {
            List<Animal> ganado = animalService.visualizarGanado();
            context.status(200).json(ganado);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera y visualiza solo los animales con estatus 'Activo'.
     * <p>Procesa la petición GET a {@code /ganado/activos}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
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

    /**
     * Recupera y visualiza los animales con estatus 'Muerto' o 'Vendido'.
     * <p>Procesa la petición GET a {@code /ganado/no-activos}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
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

    /**
     * Recupera y visualiza solo los animales con estatus 'Vendido'.
     * <p>Procesa la petición GET a {@code /ganado/vendidos}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
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

    /**
     * Recupera y visualiza un animal específico utilizando su ID de arete.
     * <p>Procesa la petición GET a {@code /ganado/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verUnGanado(Context context){
        try {
            int idArete = Integer.parseInt(context.pathParam("id"));
            Animal ganado = animalService.verUnSoloGanado(idArete);

            if (ganado == null) {
                context.status(404).json(Map.of("estado", false, "mensaje", "Ganado con ID " + idArete + " no encontrado."));
                return;
            }
            context.status(200).json(ganado);

        } catch (NumberFormatException e){
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID del arete debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Procesa la baja de un animal (actualiza su estatus a 'Muerto' o 'Vendido' y registra la fecha de baja).
     * <p>Procesa la petición PATCH a {@code /ganado/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void darBajaGanado(Context context) {
        try {
            int idArete = Integer.parseInt(context.pathParam("id"));
            Animal ganadoBaja = context.bodyAsClass(Animal.class);
            ganadoBaja.setIdArete(idArete);

            AnimalValidator animalValidator = new AnimalValidator();

            Map<String, String> errores = animalValidator.validarAnimalBaja(ganadoBaja);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            animalService.darBajaGanado(ganadoBaja);

            context.status(200).json(Map.of("estado", true, "mensaje", "Ganado dado de baja con éxito", "data", ganadoBaja));

        }catch (NumberFormatException e){
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID del arete debe ser un número entero válido."));
        }catch (IllegalArgumentException e){
            String mensajeError = e.getMessage() != null ? e.getMessage() : "Error de validación en la baja del ganado.";
            if (mensajeError.contains("Ganado no encontrado") || mensajeError.contains("Ganado inexistente")) {
                context.status(404).json(Map.of("estado", false, "mensaje", "Ganado inexistente o ya dado de baja."));
            } else {
                context.status(400).json(Map.of("estado", false, "mensaje", mensajeError));
            }
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}