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
     * <p>Procesa la petición POST a {@code /alimentos}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void guardarAlimentos(Context context) {
        try {
            Alimento nuevoAlimento = context.bodyAsClass(Alimento.class);
            AlimentoValidator alimentoValidator = new AlimentoValidator();

            Map<String, String> errores = alimentoValidator.validarAlimento(nuevoAlimento);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            alimentoService.guardarAlimentos(nuevoAlimento);

            context.status(201).json(Map.of("estado", true, "mensaje", "Compra de alimento registrada con éxito", "data", nuevoAlimento));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Recupera y visualiza todas las compras de alimentos registradas.
     * <p>Procesa la petición GET a {@code /alimentos}.</p>
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
     * <p>Procesa la petición PATCH a {@code /alimentos/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void editarAlimentos(Context context){
        try {
            int idCompra = Integer.parseInt(context.pathParam("id"));
            Alimento alimentoActualizar = context.bodyAsClass(Alimento.class);

            Alimento alimentoOriginal = alimentoService.encontrarAlimento(idCompra);
            if (alimentoOriginal == null)
                throw new NotFoundResponse("Compra no encontrada");

            alimentoActualizar.setIdCompra(idCompra);
            AlimentoValidator alimentoValidator = new AlimentoValidator();
            Map<String, String> errores = alimentoValidator.validarAlimento(alimentoActualizar);

            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("estado", false, "errores", errores));
                return;
            }

            alimentoService.editarAlimentos(alimentoActualizar);

            context.status(200).json(Map.of("estado", true, "mensaje", "Compra actualizada con éxito", "data", alimentoActualizar));
        } catch (NotFoundResponse e){
            context.status(404).json(Map.of("estado", false, "mensaje", e.getMessage()));
        } catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID de la compra debe ser un número entero válido."));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError() );
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    /**
     * Elimina un registro de compra de alimento por su ID.
     * <p>Procesa la petición DELETE a {@code /alimentos/{id}}.</p>
     *
     * @param context El contexto de la petición HTTP de Javalin.
     */
    public void eliminarAlimento (Context context){
        try {
            int idCompra = Integer.parseInt(context.pathParam("id"));

            Alimento alimentoOriginal = alimentoService.encontrarAlimento(idCompra);
            if (alimentoOriginal == null)
                throw new NotFoundResponse("Compra no encontrada");

            alimentoService.eliminarAlimento(idCompra);

            context.status(204);
        }catch(NotFoundResponse e){
            context.status(404).json(Map.of("estado", false, "mensaje", e.getMessage()));
        }catch (NumberFormatException e) {
            context.status(400).json(Map.of("estado", false, "mensaje", "El ID de la compra debe ser un número entero válido."));
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}