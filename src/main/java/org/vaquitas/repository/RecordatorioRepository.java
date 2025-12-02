package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Recordatorio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Recordatorio}.
 * <p>
 * Es responsable de gestionar la conexión a la base de datos y ejecutar sentencias SQL.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RecordatorioRepository {

    /**
     * Persiste un nuevo registro de recordatorio en la base de datos.
     *
     * @param recordatorio El objeto {@link Recordatorio} a guardar, que contiene la fecha programada.
     * @return El ID generado (clave primaria) del nuevo recordatorio insertado.
     * @throws SQLException Si ocurre un error de base de datos o si no se puede obtener el ID generado.
     */
    public int save(Recordatorio recordatorio) throws SQLException {
        String sql ="INSERT INTO RECORDATORIO (fecha) VALUES (?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Date sqlDate = Date.valueOf(recordatorio.getFechaRecordatorio());
            statement.setDate(1, sqlDate);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) return resultSet.getInt(1);
            throw new SQLException("No se generó el ID del recordatorio.");
        }
    }

    /**
     * Recupera una lista de todos los recordatorios cuya fecha sea igual o posterior a la fecha actual.
     *
     * @return Una lista de objetos {@link Recordatorio} futuros o del día de hoy.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Recordatorio> findAll() throws SQLException{
        List<Recordatorio> recordatorios = new ArrayList<>();
        // Solo se muestran recordatorios activos (hoy o futuros)
        LocalDate hoy = LocalDate.now();
        String sql = "SELECT calendario_id, fecha FROM RECORDATORIO WHERE fecha >= ?";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setDate(1, java.sql.Date.valueOf(hoy));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Recordatorio recordatorio = new Recordatorio();
                    recordatorio.setIdRecordatorio(resultSet.getInt("calendario_id"));
                    Date sqlDate = resultSet.getDate("fecha");
                    recordatorio.setFechaRecordatorio(sqlDate.toLocalDate());
                    recordatorios.add(recordatorio);
                }
            }
            return recordatorios;
        }
    }

    /**
     * Busca un recordatorio existente por su fecha.
     * <p>
     * Se utiliza para evitar duplicados si la misma fecha de recordatorio ya fue programada.
     * Si lo encuentra, devuelve el ID del registro existente.
     * </p>
     *
     * @param recordatorio El objeto {@link Recordatorio} que contiene la fecha a buscar.
     * @return El objeto {@link Recordatorio} con el ID si es encontrado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Recordatorio search(Recordatorio recordatorio) throws SQLException{
        String sql = "SELECT calendario_id FROM RECORDATORIO WHERE fecha = ?";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            Date fechasql = Date.valueOf(recordatorio.getFechaRecordatorio());
            statement.setDate(1, fechasql);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Si se encuentra, se inyecta el ID al objeto pasado como parámetro
                    recordatorio.setIdRecordatorio(resultSet.getInt("calendario_id"));
                    return recordatorio;
                }
                return null;
            }
        }
    }

    /**
     * Actualiza la fecha de un recordatorio existente.
     *
     * @param recordatorio El objeto {@link Recordatorio} con el ID y la nueva fecha a establecer.
     * @return El número de filas afectadas (1 si fue exitoso, 0 si no se encontró el ID).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public int update(Recordatorio recordatorio) throws SQLException{
        String sql = "UPDATE RECORDATORIO SET fecha = ? WHERE calendario_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            Date sqlDate = Date.valueOf(recordatorio.getFechaRecordatorio());
            statement.setDate(1, sqlDate);
            statement.setInt(2, recordatorio.getIdRecordatorio());
            return statement.executeUpdate();
        }
    }
}