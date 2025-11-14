package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Receta;
import org.vaquitas.model.Recordatorio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecordatorioRepository {

    //AGREGAR EL RECORDATORIO
    public int save(Recordatorio recordatorio) throws SQLException {
        String sql ="INSERT INTO RECORDATORIO (fecha) VALUES (?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            Date sqlDate = Date.valueOf(recordatorio.getFechaRecordatorio());
            statement.setDate(1, sqlDate);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) return resultSet.getInt(1);
            // CORRECCIÓN del mensaje de error
            throw new SQLException("No se generó el recordatorio.");
        }
    }

    public List<Recordatorio> findAll() throws SQLException{
        List<Recordatorio> recordatorios = new ArrayList<>();
        String sql = "SELECT * FROM RECORDATORIO";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Recordatorio recordatorio = new Recordatorio();
                recordatorio.setIdRecordatorio(resultSet.getInt("calendario_id"));
                Date sqlDate = resultSet.getDate("fecha");
                recordatorio.setFechaRecordatorio(sqlDate.toLocalDate());
                recordatorios.add(recordatorio);
            }
            return recordatorios;
        }
    }

    //MICROSERVICIOS Y VALIDACIONES

    public Recordatorio search(Recordatorio recordatorio) throws SQLException{
        // CORRECCIÓN: La consulta debe seleccionar el ID, no solo '1'.
        // Asumiendo que el ID se llama calendario_id.
        String sql = "SELECT calendario_id FROM RECORDATORIO WHERE fecha = ?";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            Date fechasql = Date.valueOf(recordatorio.getFechaRecordatorio());
            statement.setDate(1, fechasql);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    recordatorio.setIdRecordatorio(resultSet.getInt("calendario_id"));
                    return recordatorio;
                }
                return null;
            }
        }
    }
}
