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
 * Es responsable de recibir peticiones HTTP (vía {@link org.vaquitas.route.AnimalRoute}),
 * realizar validaciones y llamar a la capa de servicio ({@link AnimalService}).
 * Maneja la conversión de datos (JSON) y la respuesta al cliente (status codes y mensajes de error).
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
     * <p>
     * Procesa la petición POST a /ganado.
     * Realiza validación de campos y maneja errores comunes como arete duplicado o raza inexistente.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
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
            // Respuesta exitosa de creación
            context.status(201).json(Map.of("estado", true,"data", nuevoGanado));
        } catch (IllegalArgumentException e){
            // Manejo de errores de negocio (Raza no encontrada, Arete duplicado)
            String mensaje = e.getMessage();
            if (mensaje.contains("Raza no encontrada")) {
                context.status(404).json(Map.of("mensaje", mensaje));
            } else if (mensaje.contains("Arete duplicado")) {
                context.status(409).json(Map.of("mensaje", mensaje)); // 409 Conflict
            } else {
                context.status(400).json(Map.of("mensaje", mensaje)); // 400 Bad Request
            }
        } catch (SQLException e) {
            // Error de conexión o SQL
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            // Error inesperado en el servidor
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera y visualiza todos los animales (activos e inactivos) registrados.
     * <p>
     * Procesa la petición GET a /ganado.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     * @throws SQLException Si ocurre un error de base de datos.
     */
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

    /**
     * Recupera y visualiza solo los animales con estatus 'Activo'.
     * <p>
     * Procesa la petición GET a /ganado/activos.
     * </p>
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
     * <p>
     * Procesa la petición GET a /ganado/no-activos.
     * </p>
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
     * <p>
     * Procesa la petición GET a /ganado/vendidos.
     * </p>
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
     * <p>
     * Procesa la petición GET a /ganado/{id}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verUnGanado(Context context){
        try {
            // Intenta convertir el parámetro de ruta 'id' a entero
            int idArete = Integer.parseInt(context.pathParam("id"));
            Animal ganado = animalService.verUnSoloGanado(idArete);

            if (ganado == null) {
                // Ganado no encontrado
                context.status(404).json(Map.of("mensaje", "Ganado con ID " + idArete + " no encontrado."));
                return;
            }
            context.status(200).json(ganado);

        } catch (NumberFormatException e){
            // El parámetro 'id' no es un número
            context.status(400).json(Map.of("mensaje", "El ID del arete debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Procesa la baja de un animal (actualiza su estatus a 'Muerto' y registra la fecha de baja).
     * <p>
     * Procesa la petición PATCH a /ganado/{id}.
     * Realiza validación de la fecha de baja.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void darBajaGanado(Context context) throws SQLException {
        try {
            int idArete = Integer.parseInt(context.pathParam("id"));
            Animal ganadoMuerto = context.bodyAsClass(Animal.class);

            // Se asegura que el ID del arete del path se use en el objeto
            ganadoMuerto.setIdArete(idArete);

            AnimalValidator animalValidator = new AnimalValidator();
            Map<String, String> errores = animalValidator.validarAnimalBaja(ganadoMuerto);

            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }

            animalService.darBajaGanado(ganadoMuerto);

            // Respuesta exitosa
            context.status(200).json(Map.of("estado", true,"data", ganadoMuerto));

        }catch (NumberFormatException e){
            // El ID del arete no es un número
            context.status(400).json(Map.of("mensaje", "El ID del arete debe ser un número entero válido."));
        }catch (IllegalArgumentException e){
            // Errores de negocio (Animal no encontrado, Fecha de baja inválida)
            String mensajeError = e.getMessage() != null ? e.getMessage() : "Error de validación en la baja del ganado.";
            if (mensajeError.contains("Ganado no encontrado")) {
                context.status(404).json(Map.of("mensaje", "Ganado inexistente o ya dado de baja."));
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