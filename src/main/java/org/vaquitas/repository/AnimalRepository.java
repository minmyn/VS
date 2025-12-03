package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Raza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Animal}.
 * <p>
 * Es responsable de traducir las operaciones de negocio en sentencias SQL y manejar las conexiones a la base de datos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AnimalRepository {

    /**
     * Persiste un nuevo registro de animal en la tabla ANIMAL.
     *
     * @param animal El objeto {@link Animal} a guardar.
     * @return El número de filas afectadas (1).
     * @throws SQLException Si ocurre un error durante la ejecución de la sentencia SQL (e.g., violaciones de restricciones).
     */
    public int save(Animal animal) throws SQLException {
        String sql =
                "INSERT INTO ANIMAL (arete_id, nombre, fecha_nacimiento, peso, sexo, raza_id) " +
                "VALUES(?,?,?,?,?,?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, animal.getIdArete());
            statement.setString(2, animal.getNombre());
            statement.setDate(3, Date.valueOf(animal.getFechaNacimiento()));
            statement.setDouble(4, animal.getPeso());
            statement.setString(5, animal.getSexo());
            statement.setInt(6, animal.getRaza().getIdRaza());
            return statement.executeUpdate();
        }
    }

    /**
     * Recupera una lista de todos los animales registrados, incluyendo su información de {@link Raza}.
     *
     * @return Una lista de objetos {@link Animal}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> findAll() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql =
                "SELECT a.*, r.raza_id, r.nombre as nombre_raza " +
                "FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                animals.add(mapAnimal(resultSet));
            }
        }
        return animals;
    }

    /**
     * Recupera una lista de animales con estatus 'Activo'.
     *
     * @return Una lista de objetos {@link Animal} activos.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> findActivo() throws SQLException {
        List<Animal> ganadoActivo = new ArrayList<>();
        String sql = "SELECT a.*, r.raza_id, r.nombre AS nombre_raza " +
                "FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id " +
                "WHERE estado = 'Activo'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ganadoActivo.add(mapAnimal(resultSet));
            }
        }
        return ganadoActivo;
    }

    /**
     * Recupera una lista de animales con estatus 'Muerto' o 'Vendido'.
     *
     * @return Una lista de objetos {@link Animal} no activos.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> findNoActivo() throws SQLException {
        List<Animal> ganadoNoActivo = new ArrayList<>();
        String sql = "SELECT a.*, r.raza_id, r.nombre AS nombre_raza " +
                "FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id " +
                "WHERE estado = 'Muerto' OR estado = 'Vendido' ORDER BY a.arete_id";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ganadoNoActivo.add(mapAnimal(resultSet));
            }
        }
        return ganadoNoActivo;
    }

    /**
     * Recupera una lista de animales con estatus 'Vendido'.
     *
     * @return Una lista de objetos {@link Animal} vendidos.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Animal> findVendido() throws SQLException {
        List<Animal> ganadoVendido = new ArrayList<>();
        String sql = "SELECT a.*, r.raza_id, r.nombre AS nombre_raza " +
                "FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id " +
                "WHERE estado = 'Vendido'";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ganadoVendido.add(mapAnimal(resultSet));
            }
        }
        return ganadoVendido;
    }

    /**
     * Busca un animal por su ID de arete.
     *
     * @param idArete El ID del arete a buscar.
     * @return El objeto {@link Animal} si es encontrado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Animal findGanado(int idArete) throws SQLException{
        String sql = "SELECT a.*, r.raza_id, r.nombre as nombre_raza " +
                "FROM ANIMAL a JOIN RAZA r ON a.raza_id = r.raza_id " +
                "WHERE a.arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idArete);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAnimal(resultSet);
                }
            }
            return null;
        }
    }

    /**
     * Actualiza la fecha de baja y el estado (a 'Muerto') de un animal.
     *
     * @param animal El objeto {@link Animal} conteniendo el ID del arete y la fecha de baja.
     * @return El número de filas afectadas (1 si fue exitoso, 0 si el animal no existe).
     * @throws SQLException Si ocurre un error de base de datos.
     */
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

    /**
     * Verifica si un ID de arete ya existe en la base de datos.
     *
     * @param idArete El ID del arete a verificar.
     * @return {@code true} si el arete ya existe, {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean existsByIdArete(int idArete) throws SQLException {
        String sql = "SELECT 1 FROM ANIMAL WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idArete);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    /**
     * Recupera la fecha de nacimiento y el estado actual de un animal para validación de venta.
     *
     * @param idArete El ID del arete.
     * @return Un objeto {@link Animal} con la fecha de nacimiento y el estado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Animal validateVenta(int idArete) throws SQLException {
        String sql = "SELECT fecha_nacimiento, estado FROM ANIMAL WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idArete);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Animal ganado = new Animal();
                Date sqlDate = resultSet.getDate("fecha_nacimiento");
                ganado.setFechaNacimiento(sqlDate.toLocalDate());
                ganado.setEstatus(resultSet.getString("estado"));
                return ganado;
            }
        }
        return null;
    }

    /**
     * Valida el estatus del animal para operaciones de cuidado (Receta/Consulta).
     * <p>
     * Se utiliza para asegurar que las operaciones de salud solo se realicen en ganado 'Activo'.
     * </p>
     *
     * @param idArete El ID del arete a verificar.
     * @return {@code true} si el animal **NO** está 'Activo' (incluye no existente, 'Muerto', o 'Vendido'), {@code false} si está 'Activo'.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public boolean validateCuidado(int idArete) throws SQLException{
        String sql = "SELECT estado FROM ANIMAL WHERE arete_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idArete);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next())
                    return true; // Retorna true si no existe
                String estado = resultSet.getString("estado");
                return !"Activo".equalsIgnoreCase(estado); // Retorna true si es 'Muerto' o 'Vendido'
            }
        }
    }

    /**
     * Recupera la fecha de nacimiento de un animal para ser utilizada en la validación de la fecha de baja (Muerte/Venta).
     *
     * @param idArete El ID del arete.
     * @return Un objeto {@link Animal} que contiene únicamente la fecha de nacimiento, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
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

    /**
     * Función utilitaria para mapear una fila de {@link ResultSet} a un objeto {@link Animal}.
     * <p>
     * Se usa en los métodos de consulta para evitar la duplicidad de código.
     * </p>
     *
     * @param rs El {@link ResultSet} posicionado en la fila actual.
     * @return Un objeto {@link Animal} completamente mapeado.
     * @throws SQLException Si ocurre un error al acceder a una columna del ResultSet.
     */
    private Animal mapAnimal(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setIdArete(rs.getInt("arete_id"));
        animal.setNombre(rs.getString("nombre"));
        animal.setPeso(rs.getDouble("peso"));
        animal.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
        animal.setEstatus(rs.getString("estado"));
        animal.setSexo(rs.getString("sexo"));
        Date fechaBajaSql = rs.getDate("fecha_baja");
        if (fechaBajaSql != null) animal.setFechaBaja(fechaBajaSql.toLocalDate());
        Raza raza = new Raza();
        raza.setIdRaza(rs.getInt("raza_id"));
        raza.setNombreRaza(rs.getString("nombre_raza"));
        animal.setRaza(raza);
        return animal;
    }
}