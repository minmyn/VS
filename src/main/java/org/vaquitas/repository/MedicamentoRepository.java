package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Medicamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepository {

    //GUARDAR MEDICAMENTO
    public int save(Medicamento medicamento) throws SQLException {
        String sql="INSERT INTO MEDICAMENTO (nombre, descripcion) VALUES (?, ?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, medicamento.getNombre());
            statement.setString(2, medicamento.getDescripcion());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
            throw new SQLException("No se generó el medicamento.");
        }
    }

    //CATALOGO DE MEDICAMENTOS
    public List<Medicamento> findAll() throws SQLException{
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM MEDICAMENTO";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Medicamento medicamento = new Medicamento();
                medicamento.setIdMedicamento(resultSet.getInt("medicamento_id"));
                medicamento.setNombre(resultSet.getString("nombre"));
                medicamento.setDescripcion(resultSet.getString("descripcion"));
                medicamentos.add(medicamento);
            }
            return medicamentos;
        }
    }

    //ACUALIZAR MEDICAMENTO
    public int update(Medicamento medicamento) throws SQLException {
        String sql = "UPDATE MEDICAMENTO SET nombre = ? WHERE medicamento_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, medicamento.getNombre());
            statement.setInt(2, medicamento.getIdMedicamento());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La actualización no afectó ninguna fila.");
            }
            return affectedRows;
        }
    }

    //PEQUEO BUSCADOR DE MEDICAMENTOS
    public List<Medicamento> findByNombre(String texto) throws SQLException {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql;
        String param;
        if (texto == null || texto.isEmpty()) {
            sql = "SELECT * FROM MEDICAMENTO";
            param = null;
        } else if (texto.length() <= 1) {
            sql = "SELECT * FROM MEDICAMENTO WHERE nombre LIKE ?";
            param = texto + "%";
        } else {
            sql = "SELECT * FROM MEDICAMENTO WHERE nombre LIKE ?";
            param = "%" + texto + "%";
        }
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (param != null)
                statement.setString(1, param);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Medicamento medicamento = new Medicamento();
                    medicamento.setIdMedicamento(resultSet.getInt("medicamento_id"));
                    medicamento.setNombre(resultSet.getString("nombre"));
                    medicamento.setDescripcion(resultSet.getString("descripcion"));
                    medicamentos.add(medicamento);
                }
            }
        }
        return medicamentos;
    }
}