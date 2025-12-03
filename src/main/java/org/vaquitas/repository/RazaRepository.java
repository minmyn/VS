package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Raza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Raza}.
 * <p>
 * Es responsable de gestionar la conexión a la base de datos y ejecutar sentencias SQL.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class RazaRepository {

    /**
     * Persiste un nuevo registro de raza en la tabla RAZA y recupera su ID generado.
     *
     * @param raza El objeto {@link Raza} a guardar.
     * @return El ID (clave primaria) generado por la base de datos.
     * @throws SQLException Si ocurre un error de base de datos o si no se genera el ID.
     */
    public int save(Raza raza) throws SQLException{
        String sql="INSERT INTO RAZA (nombre) VALUES (?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,raza.getNombreRaza());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
            throw new SQLException("No se generó la clave primaria para la nueva raza.");
        }
    }

    /**
     * Recupera una lista de todas las razas registradas.
     *
     * @return Una lista de objetos {@link Raza}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
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

    /**
     * Modifica el nombre de una raza existente.
     *
     * @param raza El objeto {@link Raza} con el ID y el nuevo nombre.
     * @return El número de filas afectadas (1 si la actualización fue exitosa).
     * @throws SQLException Si ocurre un error de base de datos o si no se afecta ninguna fila.
     */
    public int update(Raza raza) throws SQLException{
        String sql = "UPDATE RAZA SET nombre = ? WHERE raza_id = ?";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, raza.getNombreRaza());
            statement.setInt(2, raza.getIdRaza());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("La actualización de la raza no afectó ninguna fila. ID no encontrado.");
            return affectedRows;
        }
    }

    /**
     * Busca y recupera una raza por su ID o su nombre.
     * <p>
     * Nota: Prioriza la búsqueda por nombre, pero el ID también se puede usar si está presente.
     * </p>
     *
     * @param raza El objeto {@link Raza} conteniendo el nombre o el ID a buscar.
     * @return El objeto {@link Raza} si es encontrado, o {@code null}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
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

    /**
     * Verifica si un nombre de raza ya existe en la base de datos (Validación de duplicados).
     *
     * @param nombreRaza El nombre de la raza a verificar.
     * @return {@code true} si el nombre ya existe, {@code false} en caso contrario.
     * @throws SQLException Si ocurre un error de base de datos.
     */
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