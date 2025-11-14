package org.vaquitas.controller;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import org.vaquitas.model.Alimento;
import org.vaquitas.service.AlimentoService;
import org.vaquitas.util.AlimentoValidator;
import org.vaquitas.util.Error;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlimentoControl {
    private final AlimentoService alimentoService;
    public AlimentoControl(AlimentoService alimentoService) {
        this.alimentoService=alimentoService;
    }

    //GUARDAR ALIMENTOS
    public void guardarAlimentos(Context context) {
        try {
            Alimento nuevoAlimento = context.bodyAsClass(Alimento.class);
            AlimentoValidator alimentoValidator = new AlimentoValidator();
            Map<String, String> errores = alimentoValidator.validarAlimento(nuevoAlimento);
            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }
            alimentoService.guardarAlimentos(nuevoAlimento);
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", nuevoAlimento);
            context.status(201).json(respuesta);
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

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

    // Corregido: La lógica de actualización estaba al revés (respuesta antes de servicio).
    // También se asigna el ID del pathParam al objeto para la actualización.
    public void editarAlimentos(Context context){
        try {
            int idCompra = Integer.parseInt(context.pathParam("id"));
            Alimento alimentoActualizar = context.bodyAsClass(Alimento.class);

            // Verificar si el recurso existe
            Alimento alimentoOriginal = alimentoService.encontrarAlimento(idCompra);
            if (alimentoOriginal == null){
                throw new NotFoundResponse("Compra no encontrada");
            }

            // Asignar el ID del pathParam al objeto que se va a actualizar
            alimentoActualizar.setIdCompra(idCompra);

            AlimentoValidator alimentoValidator = new AlimentoValidator();
            Map<String, String> errores = alimentoValidator.validarAlimento(alimentoActualizar);

            if (!errores.isEmpty()) {
                context.status(400).json(Map.of("errores", errores));
                return;
            }

            // 1. Ejecutar el servicio de actualización ANTES de enviar la respuesta
            alimentoService.editarAlimentos(alimentoActualizar);

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("estado", true);
            respuesta.put("data", alimentoActualizar);
            // 2. Usar 200/OK y enviar la respuesta después de la actualización exitosa
            context.status(200).json(respuesta);

        } catch (NotFoundResponse e){
            context.status(404).json(Map.of("mensaje", e.getMessage()));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError() );
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

    public void eliminarAlimento (Context context){
        try{
            int idCompra = Integer.parseInt(context.pathParam("id"));
            alimentoService.eliminarAlimento(idCompra);
            context.status(204);
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
    //Validaciones

    //Errores
}