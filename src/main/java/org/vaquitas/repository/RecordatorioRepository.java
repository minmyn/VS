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
            throw new SQLException("No se genero la consula");
        }
    }
    //VER TODOS LOS RECORDATORIOS
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

    public Integer search(Date fechaRecordatorio) throws SQLException{
        String sql = "SELECT * FROM RECORDATORIO WHERE fecha = ?";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, fechaRecordatorio);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Recordatorio recordatorio = new Recordatorio();
                    int idRecordatorio = resultSet.getInt("calendario_id");
                    return idRecordatorio;
                }
                return null;
            }
        }
    }
}
