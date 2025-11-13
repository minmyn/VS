package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {

    // Registrar ganado
    public void save(Animal animal) throws SQLException {
        String sql = "INSERT INTO ANIMAL(arete_id, nombre, fecha_nacimiento, peso, sexo, raza_id) VALUES(?,?,?,?,?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, animal.getIdArete());
            statement.setString(2, animal.getNombre());
            statement.setDate(3, Date.valueOf(animal.getFechaNacimiento()));
            statement.setDouble(4, animal.getPeso());
            statement.setString(5, animal.getSexo());
            statement.setInt(6, animal.getRaza().getIdRaza());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("La inserción del animal no afectó ninguna fila.");
            }
        }
    }

    // Visualizar todos los animales
    public List<Animal> findAll() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT a.*, r.raza_id, r.nombre as nombre_raza FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                animals.add(mapAnimal(resultSet));
            }
        }
        return animals;
    }

    // Ganado activo
    public List<Animal> findActivo() throws SQLException {
        List<Animal> ganadoActivo = new ArrayList<>();
        String sql = "SELECT a.*, r.raza_id, r.nombre AS nombre_raza FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id WHERE estado = 'Activo'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ganadoActivo.add(mapAnimal(resultSet));
            }
        }
        return ganadoActivo;
    }

    // Ganado no activo (Muerto o Vendido)
    public List<Animal> findNoActivo() throws SQLException {
        List<Animal> ganadoNoActivo = new ArrayList<>();
        String sql = "SELECT a.*, r.raza_id, r.nombre AS nombre_raza FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id WHERE estado = 'Muerto' OR estado = 'Vendido'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ganadoNoActivo.add(mapAnimal(resultSet));
            }
        }
        return ganadoNoActivo;
    }

    // Ganado vendido
    public List<Animal> findVendido() throws SQLException {
        List<Animal> ganadoVendido = new ArrayList<>();
        String sql = "SELECT a.*, r.raza_id, r.nombre AS nombre_raza FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id WHERE estado = 'Vendido'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ganadoVendido.add(mapAnimal(resultSet));
            }
        }
        return ganadoVendido;
    }

    // Dar de baja al ganado
    public int update(Animal animal) throws SQLException {
        String sql = "UPDATE ANIMAL SET fecha_baja = ? , estado = 'Muerto' WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            Date sqlDate = Date.valueOf(animal.getFechaBaja());
            statement.setDate(1, sqlDate);
            statement.setInt(2, animal.getIdArete());
            return statement.executeUpdate();
        }
    }

    // Verifica si existe por arete
    public boolean existsByIdArete(int idArete) throws SQLException {
        String sql = "SELECT 1 FROM ANIMAL WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idArete);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }
    }

    // Validar fecha de baja, sólo obtiene la fecha de nacimiento
    public Animal validateFechaBaja(int idArete) throws SQLException {
        String sql = "SELECT fecha_nacimiento FROM ANIMAL WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idArete);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Animal ganado = new Animal();
                Date sqlDate = resultSet.getDate("fecha_nacimiento");
                ganado.setFechaNacimiento(sqlDate.toLocalDate());
                return ganado;
            }
        }
        return null;
    }

    // Funcion pa' no andar repitiendo codigo tilin :D
    private Animal mapAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setIdArete(rs.getInt("arete_id"));
        animal.setNombre(rs.getString("nombre"));
        animal.setPeso(rs.getDouble("peso"));
        animal.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        animal.setEstatus(rs.getString("estado"));
        animal.setSexo(rs.getString("sexo"));
        animal.setIdRancho(rs.getInt("rancho_id"));
        Date fechaBajaSql = rs.getDate("fecha_baja");
        if (fechaBajaSql != null) animal.setFechaBaja(fechaBajaSql.toLocalDate());

        // Mapear Raza
        Raza raza = new Raza();
        raza.setIdRaza(rs.getInt("raza_id"));
        raza.setNombreRaza(rs.getString("nombre_raza"));
        animal.setRaza(raza);

        return animal;
    }
}
