package org.vaquitas.service;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;
import org.vaquitas.model.DTOdetalles;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;
import org.vaquitas.repository.AnimalRepository;
import org.vaquitas.repository.ConsultaRepository;
import org.vaquitas.repository.RecetaRepository;
import org.vaquitas.repository.RecordatorioRepository;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Receta} y sus entidades asociadas.
 * <p>
 * Maneja la **transaccionalidad** de la creación de Consulta, Recordatorio y Receta.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecetaService {
    private final RecetaRepository recetaRepository;
    private final RecordatorioRepository recordatorioRepository;
    private final ConsultaRepository consultaRepository;
    private final AnimalRepository animalRepository;

    /**
     * Constructor que inyecta las dependencias de los repositorios.
     *
     * @param recetaRepository Repositorio de Receta.
     * @param recordatorioRepository Repositorio de Recordatorio.
     * @param consultaRepository Repositorio de Consulta.
     * @param animalRepository Repositorio de Animal (para validaciones).
     */
    public RecetaService(RecetaRepository recetaRepository,
                         RecordatorioRepository recordatorioRepository,
                         ConsultaRepository consultaRepository,
                         AnimalRepository animalRepository) {
        this.recetaRepository = recetaRepository;
        this.recordatorioRepository = recordatorioRepository;
        this.consultaRepository = consultaRepository;
        this.animalRepository = animalRepository;
    }

    /**
     * Guarda una nueva receta de forma **transaccional**, asegurando que todas las entidades
     * (Consulta, Recordatorio y Receta) se creen o ninguna se cree (rollback).
     * <p>
     * Reglas de negocio:
     * <ul>
     * <li>Verifica que el animal exista y esté en estatus 'Activo'.</li>
     * <li>Si el {@link Recordatorio} con la misma fecha ya existe, lo reutiliza.</li>
     * <li>Crea la {@link Consulta} primero para obtener su ID.</li>
     * </ul>
     * </p>
     *
     * @param receta El objeto {@link Receta} con sus entidades relacionadas.
     * @throws SQLException Si ocurre un error de base de datos (se realiza rollback).
     * @throws IllegalArgumentException Si el animal no existe o no está activo.
     */
    public void guardarReceta(Receta receta) throws SQLException {
        // Inicia la transacción
        try (Connection connection = DatabaseConfig.getDataSource().getConnection()) {
            connection.setAutoCommit(false); // Inicia la transacción
            try {
                Consulta consulta = receta.getConsulta();
                int idArete = consulta.getGanado().getIdArete();

                // 1. Validación de negocio: Ganado activo/existente
                if (!animalRepository.validateCuidado(idArete))
                    throw new IllegalArgumentException("El ganado no existe ó no está activo.");

                // 2. Manejar Recordatorio (Reutilizar si existe, o crear si es nuevo)
                int idRecordatorio;
                Recordatorio recordatorioASalvar = receta.getRecordatorio();
                Recordatorio recordatorioEncontrado = recordatorioRepository.search(recordatorioASalvar);

                if (recordatorioEncontrado == null) {
                    idRecordatorio = recordatorioRepository.save(recordatorioASalvar);
                } else {
                    idRecordatorio = recordatorioEncontrado.getIdRecordatorio();
                }

                // 3. Guardar Consulta
                int idConsulta = consultaRepository.save(consulta);

                // 4. Guardar Receta
                recetaRepository.save(receta, idConsulta, idRecordatorio);

                // Commit de la transacción
                connection.commit();
            } catch (SQLException e) {
                // Rollback en caso de error
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true); // Restablece el auto-commit
            }
        }
    }

    /**
     * Recupera una lista de todos los detalles consolidados de las recetas registradas
     * (unión de Animal, Consulta, Receta, Medicamento y Recordatorio).
     *
     * @return Una lista de objetos {@link DTOdetalles} con la información completa.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<DTOdetalles> verDetallesRecetas() throws SQLException {
        return recetaRepository.findAllDetalles();
    }

    /**
     * Recupera una lista de detalles de recetas filtradas por un ID de medicamento específico.
     *
     * @param id El ID del medicamento para filtrar.
     * @return Una lista de objetos {@link DTOdetalles}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<DTOdetalles> verRecetaPorMedicamento(int id) throws SQLException {
        return recetaRepository.findRecetaByMedicina(id);
    }

    /**
     * Recupera una lista de detalles de recetas filtradas por un ID de recordatorio específico (ID de calendario).
     *
     * @param id El ID del recordatorio para filtrar.
     * @return Una lista de objetos {@link DTOdetalles}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<DTOdetalles> verRecetaPorRecordatorio(int id) throws SQLException {
        return recetaRepository.findRecetaByFecha(id);
    }

}