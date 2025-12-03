package org.vaquitas.route;

import io.javalin.Javalin;
import org.vaquitas.controller.MedicamentoControl;
import org.vaquitas.model.Medicamento;

/**
 * Clase de enrutamiento que define todos los {@code endpoints} relacionados con la gestión del catálogo de {@link Medicamento}.
 * <p>
 * Asigna rutas HTTP específicas a los métodos correspondientes en el {@link MedicamentoControl}.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class MedicamentoRoute {
    private final MedicamentoControl medicamentoControl;

    /**
     * Constructor que inyecta la dependencia del controlador.
     *
     * @param medicamentoControl El controlador que maneja la lógica de la petición.
     */
    public MedicamentoRoute(MedicamentoControl medicamentoControl) {
        this.medicamentoControl=medicamentoControl;
    }

    /**
     * Registra todos los {@code endpoints} de medicamentos en la aplicación Javalin.
     *
     * <table>
     * <caption>Endpoints de Medicamentos</caption>
     * <tr><th>Método</th><th>Ruta</th><th>Propósito</th></tr>
     * <tr><td>POST</td><td>/medicamentos</td><td>Registra un nuevo medicamento.</td></tr>
     * <tr><td>GET</td><td>/medicamentos</td><td>Recupera el catálogo completo de medicamentos.</td></tr>
     * <tr><td>GET</td><td>/medicamento?nombre=X</td><td>Busca medicamentos por texto en el nombre (parámetro de consulta).</td></tr>
     * <tr><td>PATCH</td><td>/medicamentos/{id}</td><td>Actualiza un medicamento por su ID.</td></tr>
     * </table>
     *
     * @param app La instancia de {@link Javalin} donde se registrarán las rutas.
     */
    public void register(Javalin app){
        app.post("/medicamentos", medicamentoControl::registrarMedicamento);
        app.get("/medicamentos", medicamentoControl::verMedicinas);
        app.get("/medicamento", medicamentoControl::buscarMedicamentosPorAlgo);
        app.patch("/medicamentos/{id}", medicamentoControl::actualizarMedicamento);
    }
}