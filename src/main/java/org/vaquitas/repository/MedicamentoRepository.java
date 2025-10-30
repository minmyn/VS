package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Medicamento;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentoRepository {

    public void save(Medicamento medicamento) throws SQLException {
        String sql="INSERT INTO medicamento (nombre) VALUES (?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, medicamento.getNombre());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
        }
    }

    public List<Medicamento> findAll() throws SQLException{
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT * FROM medicamento";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Medicamento medicamento = new Medicamento();
                medicamento.setIdMedicamento(resultSet.getInt("medicamento_id"));
                medicamento.setNombre(resultSet.getString("nombre"));
                medicamentos.add(medicamento);
            }
            return medicamentos;
        }
    }

    public int update(Medicamento medicamento) throws SQLException{
        String sql = "UPDATE medicamento SET nombre = ? WHERE medicamento_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, medicamento.getNombre());
            statement.setInt(2, medicamento.getIdMedicamento());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
            return statement.executeUpdate();
        }
    }
}