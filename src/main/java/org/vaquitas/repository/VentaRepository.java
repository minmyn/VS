package org.vaquitas.repository;


import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaRepository {

    //Registrar venta
    public void save(Venta venta) throws SQLException{
        // UPDATE ANIMAL SET fecha_baja = ?, estado = ? WHERE arete_id = ?;
        String sql="INSERT INTO VENTA (arete_id, precio_venta, peso_final, fecha_baja) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, venta.getIdArete());
            statement.setDouble(2, venta.getPrecioVenta());
            statement.setDouble(3, venta.getPesoFinal());
            Date sqlDate = Date.valueOf(venta.getFechaBaja());
            statement.setDate(4, sqlDate);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
        }
    }


    //Visualizar Ganado

    public List<Venta> findVendido() throws SQLException{
        List<Venta> animal = new ArrayList<>();
        String sql="SELECT * FROM venta";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Venta ganado = new Venta();
                ganado.setIdArete(resultSet.getInt("arete_id"));
                java.sql.Date sqlDate = resultSet.getDate("fecha_baja");
                ganado.setFechaBaja(sqlDate.toLocalDate());
                ganado.setPrecioVenta(resultSet.getDouble("precio_venta"));
                ganado.setPesoFinal(resultSet.getDouble("peso_final"));
                animal.add(ganado);
            }
            return animal;
        }
    }
}