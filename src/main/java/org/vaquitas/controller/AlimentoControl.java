package org.vaquitas.controller;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.vaquitas.model.Alimento;
import org.vaquitas.service.AlimentoService;
import org.vaquitas.util.AlimentoValidator;
import org.vaquitas.util.Error;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de la entidad {@link Alimento} (Compras de alimentos).
 * <p>
 * Es responsable de recibir peticiones HTTP, realizar validaciones y llamar a la capa de servicio ({@link AlimentoService}).
 * Maneja la serialización (JSON) y las respuestas HTTP (status codes y mensajes de error).
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AlimentoControl {

    private final AlimentoService alimentoService;

    /**
     * Constructor que inyecta la dependencia del servicio.
     *
     * @param alimentoService El servicio que contiene la lógica de negocio.
     */
    public AlimentoControl(AlimentoService alimentoService) {
        this.alimentoService=alimentoService;
    }

    /**
     * Registra una nueva compra de alimento en la base de datos.
     * <p>
     * Procesa la petición POST a /alimentos, realiza la validación de los datos.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void guardarAlimentos(Context context) {
        try {
            // 1. Deserializar y validar
            Alimento nuevoAlimento = context.bodyAsClass(Alimento.class);
            AlimentoValidator alimentoValidator = new AlimentoValidator();

            Map<String, String> errores = alimentoValidator.validarAlimento(nuevoAlimento);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            // 2. Ejecutar servicio
            alimentoService.guardarAlimentos(nuevoAlimento);
            // 3. Respuesta exitosa
            context.status(201).json(Map.of("estado", true, "data", nuevoAlimento));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera y visualiza todas las compras de alimentos registradas.
     * <p>
     * Procesa la petición GET a /alimentos.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void verAlimentos(Context context){
        try{
            List<Alimento> alimentos = alimentoService.verAlimentos();
            context.status(200).json(alimentos);
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Actualiza la información de una compra de alimento por su ID de compra.
     * <p>
     * Procesa la petición PATCH a /alimentos/{id}.
     * Requiere que la compra exista y que los datos actualizados sean válidos.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void editarAlimentos(Context context){
        try {
            int idCompra = Integer.parseInt(context.pathParam("id"));
            Alimento alimentoActualizar = context.bodyAsClass(Alimento.class);

            // 1. Verificar existencia antes de validar/actualizar
            Alimento alimentoOriginal = alimentoService.encontrarAlimento(idCompra);
            if (alimentoOriginal == null)
                throw new NotFoundResponse("Compra no encontrada");

            // 2. Asignar ID y validar
            alimentoActualizar.setIdCompra(idCompra);
            AlimentoValidator alimentoValidator = new AlimentoValidator();
            Map<String, String> errores = alimentoValidator.validarAlimento(alimentoActualizar);

            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            // 3. Ejecutar servicio
            alimentoService.editarAlimentos(alimentoActualizar);
            // 4. Respuesta exitosa
            context.status(200).json(Map.of("estado", true, "data", alimentoActualizar));
        } catch (NotFoundResponse e){
            context.status(404).json(Map.of("estado", false, "mensaje", e.getMessage()));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError() );
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Elimina un registro de compra de alimento por su ID.
     * <p>
     * Procesa la petición DELETE a /alimentos/{id}.
     * </p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void eliminarAlimento (Context context){
        try {
            int idCompra = Integer.parseInt(context.pathParam("id"));

            // 1. Verificar existencia antes de eliminar
            Alimento alimentoOriginal = alimentoService.encontrarAlimento(idCompra);
            if (alimentoOriginal == null)
                throw new NotFoundResponse("Compra no encontrada");

            // 2. Ejecutar servicio
            alimentoService.eliminarAlimento(idCompra);
            // 3. Respuesta exitosa (204 No Content)
            context.status(204);
        }catch(NotFoundResponse e){
            context.status(404).json(Map.of("estado", false, "mensaje", e.getMessage()));
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}