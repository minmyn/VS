package org.vaquitas.service;

import org.vaquitas.model.Medicamento;
import org.vaquitas.repository.MedicamentoRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Medicamento}.
 * <p>
 * Actúa como intermediario entre el controlador y el repositorio.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class MedicamentoService {
    private final MedicamentoRepository medicamentoRepository;

    /**
     * Constructor que inyecta la dependencia del repositorio.
     *
     * @param medicamentoRepository El repositorio para acceder a la base de datos.
     */
    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    /**
     * Guarda un nuevo medicamento en la base de datos.
     *
     * @param medicamento El objeto {@link Medicamento} a registrar.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public void registrarMedicina(Medicamento medicamento) throws SQLException {
        medicamentoRepository.save(medicamento);
    }

    /**
     * Obtiene el listado completo del catálogo de medicamentos.
     *
     * @return Una lista de objetos {@link Medicamento}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Medicamento> verMedicinas() throws SQLException{
        return medicamentoRepository.findAll();
    }


    /**
     * Busca medicamentos por coincidencia de texto en el campo 'nombre'.
     *
     * @param texto El fragmento de texto a buscar (usualmente con comodines como `%texto%`).
     * @return Una lista de objetos {@link Medicamento} que coinciden con la búsqueda.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Medicamento> buscarMedicamentos(String texto) throws SQLException {
        return medicamentoRepository.findByNombre(texto);
    }

    /**
     * Actualiza la información de un medicamento existente.
     *
     * @param medicamento El objeto {@link Medicamento} con el ID y la nueva información.
     * @return El número de filas afectadas (debería ser 1).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public int actualizarMedicamento(Medicamento medicamento) throws  SQLException{
        return medicamentoRepository.update(medicamento);
    }
}