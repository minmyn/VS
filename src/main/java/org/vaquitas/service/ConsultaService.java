package org.vaquitas.service;

import org.vaquitas.model.Consulta;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.ConsultaRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Consulta}.
 * <p>
 * Se encarga de la visualización de datos históricos de consultas por animal.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;

    /**
     * Constructor que inyecta las dependencias de los repositorios.
     *
     * @param consultaRepository Repositorio para operaciones de Consulta.
     * @param animalRepository Repositorio para operaciones de Animal.
     */
    public ConsultaService(ConsultaRepository consultaRepository, AnimalRepository animalRepository) {
        this.consultaRepository=consultaRepository;
        this.animalRepository = animalRepository;

    }

    /**
     * Obtiene el historial de consultas médicas asociadas a un animal específico.
     *
     * @param areteId El ID del arete del animal.
     * @return Una lista de objetos {@link Consulta} para el animal dado.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Consulta> obtenerConsultasPorArete(int areteId) throws SQLException {
        return consultaRepository.findByConsulta(areteId);
    }

}