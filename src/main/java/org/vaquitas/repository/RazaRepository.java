package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Raza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RazaRepository {
    public int save(Raza raza) throws SQLException{
        String sql="INSERT INTO RAZA (nombre) VALUES (?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,raza.getNombreRaza());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
            throw new SQLException("No se genero la consula");
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
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, raza.getNombreRaza());
            statement.setInt(2, raza.getIdRaza());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La actualización de la raza no afectó ninguna fila. ¿Existe el ID?");
            }
            return affectedRows;
        }
    }

    public Raza findRaza(Raza raza) throws SQLException{
        String sql = "SELECT * FROM RAZA WHERE nombre = ? OR raza_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, raza.getNombreRaza());
            statement.setInt(2, raza.getIdRaza());
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()){
                    Raza razaEncontrada = new Raza();
                    razaEncontrada.setIdRaza(resultSet.getInt("raza_id"));
                    razaEncontrada.setNombreRaza(resultSet.getString("nombre"));
                    return razaEncontrada;
                }
            }
        }
        return null;
    }

    public boolean existsByName(String nombreRaza) throws SQLException{
        String sql = "SELECT 1 FROM RAZA WHERE nombre = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, nombreRaza);
            try (ResultSet resultSet = statement.executeQuery()){
                return resultSet.next();
            }
        }
    }
}