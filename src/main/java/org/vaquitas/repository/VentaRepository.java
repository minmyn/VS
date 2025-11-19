package org.vaquitas.repository;


import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;
import org.vaquitas.model.Venta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaRepository {

    //REGISTRAR VENTA DE GANADO
    public void save(Venta venta) throws SQLException{
        String sql="INSERT INTO VENTA (arete_id, precio_venta, peso_final, fecha_baja) VALUES (?,?,?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, venta.getGanado().getIdArete());
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

    //VISUALIZAR GANADO VENDIDO
    public List<Venta> findVendidos() throws SQLException{
        List<Venta> animal = new ArrayList<>();
        String sql="SELECT v.venta_id, a.nombre, a.arete_id, a.sexo, a.raza_id, rz.nombre AS raza_nombre, a.fecha_nacimiento, a.peso, v.peso_final, v.precio_venta, v.fecha_baja FROM ANIMAL a INNER JOIN VENTA v ON a.arete_id = v.arete_id INNER JOIN RAZA rz ON a.raza_id = rz.raza_id WHERE a.estado = 'Vendido' ORDER BY a.arete_id";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Venta ganado = new Venta();
                Animal bovino = new Animal();
                Raza raza = new Raza();
                ganado.setIdVenta(resultSet.getInt("venta_id"));
                bovino.setNombre(resultSet.getString("nombre"));
                bovino.setIdArete(resultSet.getInt("arete_id"));
                bovino.setSexo(resultSet.getString("sexo"));
                raza.setIdRaza(resultSet.getInt("raza_id"));
                raza.setNombreRaza(resultSet.getString("raza_nombre"));
                bovino.setRaza(raza);
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

    //EDITAR VENTA
    public int update(Venta venta) throws SQLException {
        String sql = "UPDATE ANIMAL SET precio_venta, peso_final WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, venta.getPrecioVenta());
            statement.setDouble(2, venta.getPesoFinal());
            statement.setInt(3, venta.getGanado().getIdArete());
            return statement.executeUpdate();
        }
    }

    //-----------MICROSERVICIOS Ó VALIDACIONES-----------

    //¿EL GANADO YA FUE VENDIDO?
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