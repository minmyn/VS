package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Rancho;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RanchoRepository {

    public void save(Rancho rancho) throws SQLException{
        String sql="INSERT INTO RANCHO (nombre, locacion) VALUES (?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,rancho.getNombre());
            statement.setString(2, rancho.getUbicacion());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
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