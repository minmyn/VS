package org.vaquitas.repository;
import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Alimento;
import org.vaquitas.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class AlimentoRepository {

    //GUARDAR ALIMENTO
    public void save(Alimento alimento) throws SQLException {
        String sql ="INSERT INTO ALIMENTO (alimento, tipo, cantidad, precio, fecha_compra) VALUES (?, ?, ?, ?, ?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, alimento.getNombre());
            statement.setString(2, alimento.getTipo());
            statement.setDouble(3, alimento.getCantidad());
            statement.setDouble(4, alimento.getPrecio());
            Date sqlDate = Date.valueOf(alimento.getFechaCompra());
            statement.setDate(5, sqlDate);
            statement.executeUpdate();
        }
    }

    //VER TODOS LOS ALIMEMENTOS
    public List<Alimento> findAll() throws SQLException{
        List<Alimento> alimentos = new ArrayList<>();
        String sql = "SELECT * FROM ALIMENTO";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Alimento alimento = new Alimento();
                alimento.setIdCompra(resultSet.getInt("compra_id"));
                alimento.setNombre(resultSet.getString("alimento"));
                alimento.setTipo(resultSet.getString("tipo"));
                alimento.setCantidad(resultSet.getDouble("cantidad"));
                alimento.setPrecio(resultSet.getDouble("precio"));
                java.sql.Date sqlDate = resultSet.getDate("fecha_compra");
                alimento.setFechaCompra(sqlDate.toLocalDate());
                alimentos.add(alimento);
            }
            return alimentos;
        }
    }

    //ENCONTRAR-VER UN ALIMENTO
    public Alimento findAlimento(int idCompra) throws SQLException{
        String sql = "SELECT * FROM ALIMENTO WHERE compra_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCompra);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Alimento alimentoDB = new Alimento();
                    alimentoDB.setIdCompra(resultSet.getInt("compra_id"));
                    alimentoDB.setNombre(resultSet.getString("alimento"));
                    alimentoDB.setTipo(resultSet.getString("tipo"));
                    alimentoDB.setCantidad(resultSet.getDouble("cantidad"));
                    alimentoDB.setPrecio(resultSet.getDouble("precio"));
                    java.sql.Date sqlDate = resultSet.getDate("fecha_compra");
                    alimentoDB.setFechaCompra(sqlDate.toLocalDate());
                    return alimentoDB;
                }
                return null;
            }
        }
    }

    //ACTUALIZAR O CAMBIAR INFORMACION DE UN ALIMENTO
    public int update(Alimento alimento) throws SQLException{
        String sql = "UPDATE ALIMENTO SET tipo = ? , alimento = ? , cantidad = ?, precio = ? , fecha_compra = ? WHERE compra_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, alimento.getTipo());
            statement.setString(2, alimento.getNombre());
            statement.setDouble(3, alimento.getCantidad());
            statement.setDouble(4, alimento.getPrecio());
            Date sqlDate = Date.valueOf(alimento.getFechaCompra());
            statement.setDate(5, sqlDate);
            statement.setInt(6, alimento.getIdCompra());
            return statement.executeUpdate();
        }
    }

    //ELIMINAR UN ALIMENTOS
    public int delete(int idCompra) throws SQLException{
        String sql = "DELETE FROM ALIMENTO WHERE compra_id = ? ";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, idCompra);
            return statement.executeUpdate();
        }
    }


}
