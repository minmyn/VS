package org.vaquitas.service;

import org.vaquitas.model.Recordatorio;
import org.vaquitas.repository.RecordatorioRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Clase de servicio que implementa la lógica de negocio para la gestión de {@link Recordatorio}.
 * <p>
 * Sirve como intermediario entre el controlador y el repositorio, manejando las
 * operaciones de visualización y actualización de recordatorios.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecordatorioService {
    private final RecordatorioRepository recordatorioRepository;

    /**
     * Constructor que inyecta la dependencia del repositorio de recordatorios.
     *
     * @param recordatorioRepository El repositorio que maneja la persistencia de datos.
     */
    public RecordatorioService(RecordatorioRepository recordatorioRepository) {
        this.recordatorioRepository=recordatorioRepository;
    }

    /**
     * Obtiene la lista completa de todos los recordatorios programados, cuya fecha
     * sea igual o posterior al día actual (recordatorios activos).
     *
     * @return Una lista de objetos {@link Recordatorio}.
     * @throws SQLException Si ocurre un error de base de datos durante la consulta.
     */
    public List<Recordatorio> verRecordatorio() throws SQLException {
        return recordatorioRepository.findAll();
    }

    /**
     * Actualiza la fecha de un recordatorio existente en la base de datos.
     *
     * @param recordatorio El objeto {@link Recordatorio} con el ID y la nueva fecha.
     * @throws SQLException Si ocurre un error de base de datos durante la actualización.
     */
    public void editarRecordatorio(Recordatorio recordatorio) throws SQLException{
        recordatorioRepository.update(recordatorio);
    }

}