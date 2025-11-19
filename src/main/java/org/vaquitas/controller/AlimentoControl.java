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

public class AlimentoControl {

    private final AlimentoService alimentoService;
    public AlimentoControl(AlimentoService alimentoService) {
        this.alimentoService=alimentoService;
    }

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
            context.status(201).json(Map.of("estado", true, "data", nuevoAlimento));
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
            context.status(200).json(Map.of("estado", true, "data", alimentoActualizar));
        } catch (NotFoundResponse e){
            context.status(404).json(Map.of("estado", false, "mensaje", e.getMessage()));
        } catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError() );
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }

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
        }catch (SQLException e) {
            context.status(500).json(Error.getApiDatabaseError());
        } catch (Exception e) {
            context.status(500).json(Error.getApiServiceError());
        }
    }
}