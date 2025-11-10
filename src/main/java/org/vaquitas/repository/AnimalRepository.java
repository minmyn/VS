package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    //Registrar ganado
    public void save(Animal ganado) throws SQLException{
        String sql="INSERT INTO ANIMAL(arete_id, nombre, fecha_nacimiento, peso, sexo, raza_id) VALUES(?,?,?,?,?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, ganado.getIdArete());
            statement.setString(2, ganado.getNombre());
            Date sqlDate = Date.valueOf(ganado.getFechaNacimiento());
            statement.setDate(3, sqlDate);
            statement.setDouble(4, ganado.getPeso());
            statement.setString(5, ganado.getSexo());
            statement.setInt(6, ganado.getIdRaza());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del encargado no afectó ninguna fila.");
            }
        }
    }
    //Visualizar Ganado Activo
    public List<Animal> findAll() throws SQLException{
        List<Animal> animal = new ArrayList<>();
        String sql = "SELECT * FROM ANIMAL";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Animal ganado = new Animal();
                ganado.setIdArete(resultSet.getInt("arete_id"));
                ganado.setNombre(resultSet.getString("nombre"));
                ganado.setPeso(resultSet.getDouble("peso"));
                java.sql.Date sqlDate = resultSet.getDate("fecha_nacimiento");
                ganado.setFechaNacimiento(sqlDate.toLocalDate());
                ganado.setEstatus(resultSet.getString("estado"));
                ganado.setSexo(resultSet.getString("sexo"));
                ganado.setIdRaza(resultSet.getInt("raza_id"));
                animal.add(ganado);
            }
            return animal;
        }
    }
    //Ver ganado activo

    public List<Animal> findActivo()throws SQLException{
        List<Animal> ganadoActivo = new ArrayList<>();
        String sql = "SELECT * FROM ANIMAL WHERE estado = 'Activo'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Animal ganado = new Animal();
                ganado.setIdArete(resultSet.getInt("arete_id"));
                ganado.setNombre(resultSet.getString("nombre"));
                ganado.setPeso(resultSet.getDouble("peso"));
                ganado.setIdRaza(resultSet.getInt("raza_id"));
                java.sql.Date sqlDate;
                sqlDate = resultSet.getDate("fecha_nacimiento");
                ganado.setFechaNacimiento(sqlDate.toLocalDate());
                ganado.setSexo(resultSet.getString("sexo"));
                ganadoActivo.add(ganado);
            }
            return ganadoActivo;
        }
    }

    //Ver el ganado no activo
    public List<Animal> findNoActivo()throws SQLException{
        List<Animal> ganadoNoActivo = new ArrayList<>();
        String sql = "SELECT * FROM ANIMAL WHERE estado = 'Muerto' OR estado = 'Vendido'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Animal ganado = new Animal();
                ganado.setIdArete(resultSet.getInt("arete_id"));
                ganado.setNombre(resultSet.getString("nombre"));
                ganado.setPeso(resultSet.getDouble("peso"));
                ganado.setIdRaza(resultSet.getInt("raza_id"));
                ganado.setSexo(resultSet.getString("sexo"));
                ganado.setEstatus(resultSet.getString("estado"));
                Date sqlDate;
                sqlDate = resultSet.getDate("fecha_nacimiento");
                ganado.setFechaNacimiento(sqlDate.toLocalDate());
                sqlDate = resultSet.getDate("fecha_baja");
                ganado.setFechaBaja(sqlDate.toLocalDate());
                ganadoNoActivo.add(ganado);
            }
            return ganadoNoActivo;
        }
    }
    //Ver ganado Vendido

    public List<Animal> findVendido()throws SQLException{
        List<Animal> ganadoVendido = new ArrayList<>();
        String sql = "SELECT * FROM ANIMAL WHERE estado = 'Vendido'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Animal ganado = new Animal();
                ganado.setIdArete(resultSet.getInt("arete_id"));
                ganado.setNombre(resultSet.getString("nombre"));
                ganado.setPeso(resultSet.getDouble("peso"));
                ganado.setIdRaza(resultSet.getInt("raza_id"));
                java.sql.Date sqlDate;
                sqlDate = resultSet.getDate("fecha_nacimiento");
                ganado.setFechaNacimiento(sqlDate.toLocalDate());
                sqlDate = resultSet.getDate("fecha_baja");
                ganado.setFechaBaja(sqlDate.toLocalDate());
                ganado.setSexo(resultSet.getString("sexo"));
                ganadoVendido.add(ganado);
            }
            return ganadoVendido;
        }
    }

    //Dar de baja al ganado
    public int update(Animal animal) throws SQLException{
        String sql="UPDATE ANIMAL SET fecha_baja = ? , estado = 'Muerto' WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            Date sqlDate = Date.valueOf(animal.getFechaBaja());
            statement.setDate(1, sqlDate);
            statement.setInt(2,animal.getIdArete());
            return statement.executeUpdate();
        }
    }
    //No hay eliminar ganado

    //Validarchione' de la selvicioooo

    public boolean existsByIdArete (int idArete) throws SQLException{
        String sql = "SELECT * FROM ANIMAL WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, idArete);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) return true;
        }
        return false;
    }
}