package org.vaquitas.repository;
import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Alimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Alimento}.
 * <p>
 * Se encarga de traducir las operaciones del servicio en sentencias SQL y manejar la conexión a la base de datos.
 * Esta clase gestiona el registro de compras de alimentos.
 * </p>
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class AlimentoRepository {

    /**
     * Persiste un nuevo registro de compra de alimento en la tabla ALIMENTO.
     *
     * @param alimento El objeto {@link Alimento} a guardar.
     * @throws SQLException Si ocurre un error durante la ejecución de la sentencia SQL o la conexión.
     */
    public void save(Alimento alimento) throws SQLException {
        String sql =
                "INSERT INTO ALIMENTO (alimento, tipo, cantidad, precio, fecha_compra) " +
                        "VALUES (?, ?, ?, ?, ?)";
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

    /**
     * Recupera una lista de todos los registros de compras de alimentos, ordenados por fecha de compra descendente.
     *
     * @return Una lista de objetos {@link Alimento}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Alimento> findAll() throws SQLException{
        List<Alimento> alimentos = new ArrayList<>();
        String sql = "SELECT * FROM ALIMENTO ORDER BY fecha_compra DESC";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                alimentos.add(mapAlimento(resultSet));
            }
            return alimentos;
        }
    }

    /**
     * Busca y recupera un registro de alimento por su ID de compra.
     *
     * @param idCompra El ID de la compra a buscar.
     * @return El objeto {@link Alimento} si es encontrado, o {@code null} si no existe.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public Alimento findAlimento(int idCompra) throws SQLException{
        String sql = "SELECT * FROM ALIMENTO WHERE compra_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCompra);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapAlimento(resultSet);
                }
                return null;
            }
        }
    }

    /**
     * Actualiza el tipo, nombre, cantidad, precio y fecha de compra de un registro de alimento existente.
     *
     * @param alimento El objeto {@link Alimento} con la información actualizada, incluyendo el ID de compra.
     * @return El número de filas afectadas (1 si la actualización fue exitosa, 0 si no se encontró el ID).
     * @throws SQLException Si ocurre un error de base de datos.
     */
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

    /**
     * Elimina permanentemente un registro de compra de alimento de la base de datos por su ID.
     *
     * @param idCompra El ID de la compra a eliminar.
     * @return El número de filas afectadas (1 si la eliminación fue exitosa, 0 si no se encontró el ID).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public int delete(int idCompra) throws SQLException{
        String sql = "DELETE FROM ALIMENTO WHERE compra_id = ? ";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, idCompra);
            return statement.executeUpdate();
        }
    }

    /**
     * Método auxiliar para mapear un {@code ResultSet} a un objeto {@link Alimento}.
     *
     * @param resultSet El conjunto de resultados de la consulta SQL.
     * @return Un objeto {@link Alimento} completamente mapeado.
     * @throws SQLException Si ocurre un error al leer del {@code ResultSet}.
     */
    private Alimento mapAlimento(ResultSet resultSet) throws SQLException{
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
}