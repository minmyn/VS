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

    /*public List<Venta> findVendidos() throws SQLException{
        List<Venta> animal = new ArrayList<>();
        String sql="SELECT * FROM VENTA";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Venta ganado = new Venta();
                ganado.setIdVenta(resultSet.getInt("venta_id"));
                ganado.setIdArete(resultSet.getInt("arete_id"));
                java.sql.Date sqlDate = resultSet.getDate("fecha_baja");
                ganado.setFechaBaja(sqlDate.toLocalDate());
                ganado.setPrecioVenta(resultSet.getDouble("precio_venta"));
                ganado.setPesoFinal(resultSet.getDouble("peso_final"));
                animal.add(ganado);
            }
            return animal;
        }

*/
    public List<Venta> findVendidos() throws SQLException{
        List<Venta> animal = new ArrayList<>();
        String sql="SELECT v.venta_id, a.nombre, a.arete_id, a.sexo, a.raza_id, a.fecha_nacimiento, a.peso , v.peso_final, v.precio_venta, v.fecha_baja FROM ANIMAL a INNER JOIN VENTA v ON a.arete_id = v.arete_id WHERE a.estado='Vendido'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Venta ganado = new Venta();
                Animal bovino = new Animal();
                ganado.setIdVenta(resultSet.getInt("venta_id"));
                bovino.setNombre(resultSet.getString("nombre"));
                int idArete = resultSet.getInt("arete_id");
                bovino.setIdArete(idArete);
                ganado.setIdArete(idArete);
                bovino.setSexo(resultSet.getString("sexo"));
                bovino.setIdRaza(resultSet.getInt("raza_id"));
                Date sqlDate;
                sqlDate = resultSet.getDate("fecha_nacimiento");
                bovino.setFechaNacimiento(sqlDate.toLocalDate());
                bovino.setPeso(resultSet.getDouble("peso"));
                sqlDate = resultSet.getDate("fecha_baja");
                ganado.setFechaBaja(sqlDate.toLocalDate());
                ganado.setPrecioVenta(resultSet.getDouble("precio_venta"));
                ganado.setPesoFinal(resultSet.getDouble("peso_final"));
                ganado.setGanado(bovino);
                animal.add(ganado);
            }
            return animal;
        }
    }


    public boolean findVendido(int idArete)throws SQLException{
        String sql = "SELECT * FROM VENTA WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, idArete);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        }
        return false;
    }


}