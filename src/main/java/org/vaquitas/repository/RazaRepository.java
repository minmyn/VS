package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Raza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RazaRepository {
    public void save(Raza raza) throws SQLException{
        String sql="INSERT INTO RAZA (nombre) VALUES (?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,raza.getNombreRaza());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
        }

    }

    public List<Raza> findAll() throws SQLException{
        List<Raza> razas = new ArrayList<>();
        String sql = "SELECT * FROM RAZA";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Raza raza = new Raza();
                raza.setIdRaza(resultSet.getInt("raza_id"));
                raza.setNombreRaza(resultSet.getString("nombre"));
                razas.add(raza);
            }
            return razas;
        }
    }

    public int update(Raza raza) throws SQLException{
        String sql = "UPDATE RAZA SET nombre = ? WHERE raza_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, raza.getNombreRaza());
            statement.setInt(2, raza.getIdRaza());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
            return statement.executeUpdate();

        }
    }
}