package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Rancho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RanchoRepository {

    public int save(Rancho rancho) throws SQLException{
        String sql="INSERT INTO RANCHO (nombre, locacion) VALUES (?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,rancho.getNombre());
            statement.setString(2, rancho.getUbicacion());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1); // Retorna el ID generado
            throw new SQLException("No se pudo obtener el ID del rancho guardado.");
        }
    }

    public List<Rancho> findAll() throws SQLException{
        List<Rancho> ranchos = new ArrayList<>();
        String sql = "SELECT  * FROM RANCHO";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Rancho rancho = new Rancho();
                rancho.setIdRancho(resultSet.getInt("rancho_id"));
                rancho.setNombre(resultSet.getString("nombre"));
                rancho.setUbicacion(resultSet.getString("locacion"));
                ranchos.add(rancho);
            }
            return ranchos;
        }
    }

    public int update(Rancho rancho) throws SQLException{
        String sql = "UPDATE RANCHO SET locacion = ?, nombre = ? WHERE rancho_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, rancho.getUbicacion());
            statement.setString(2, rancho.getNombre());
            statement.setInt(3, rancho.getIdRancho());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
            return statement.executeUpdate();
        }
    }

    public boolean duplicateNombre(String nombre) throws SQLException{
        String sql = "SELECT * FROM RANCHO WHERE nombre = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        }
        return false;
    }
}