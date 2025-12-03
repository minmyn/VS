package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Medicamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Medicamento}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class MedicamentoRepository {

    /**
     * Persiste un nuevo medicamento en la tabla MEDICAMENTO.
     *
     * @param medicamento El objeto {@link Medicamento} a guardar.
     * @return El ID (clave primaria) generado para el nuevo medicamento.
     * @throws SQLException Si ocurre un error durante la ejecución de la sentencia SQL.
     */
    public int save(Medicamento medicamento) throws SQLException {
        String sql="INSERT INTO MEDICAMENTO (nombre, descripcion) VALUES (?, ?)";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, medicamento.getNombre());
            statement.setString(2, medicamento.getDescripcion());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
            throw new SQLException("No se pudo obtener el ID generado para el medicamento.");
        }
    }

    /**
     * Recupera todos los medicamentos del catálogo.
     *
     * @return Una lista de todos los objetos {@link Medicamento}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Medicamento> findAll() throws SQLException{
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql = "SELECT medicamento_id, nombre, descripcion FROM MEDICAMENTO ORDER BY nombre ASC";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Medicamento medicamento = new Medicamento();
                medicamento.setIdMedicamento(resultSet.getInt("medicamento_id"));
                medicamento.setNombre(resultSet.getString("nombre"));
                medicamento.setDescripcion(resultSet.getString("descripcion"));
                medicamentos.add(medicamento);
            }
            return medicamentos;
        }
    }

    /**
     * Actualiza el nombre y la descripción de un medicamento por su ID.
     *
     * @param medicamento El objeto {@link Medicamento} con el ID y el nuevo nombre/descripción.
     * @return El número de filas afectadas (1 si la actualización fue exitosa).
     * @throws SQLException Si ocurre un error de base de datos o si no se afecta ninguna fila.
     */
    public int update(Medicamento medicamento) throws SQLException {
        String sql = "UPDATE MEDICAMENTO SET nombre = ?, descripcion = ? WHERE medicamento_id = ?";
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, medicamento.getNombre());
            statement.setString(2, medicamento.getDescripcion());
            statement.setInt(3, medicamento.getIdMedicamento());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se encontró ningún medicamento con el ID: " + medicamento.getIdMedicamento() + " para actualizar.");
            }
            return affectedRows;
        }
    }

    /**
     * Busca medicamentos por coincidencias en el campo 'nombre'.
     * <p>
     * La lógica de búsqueda varía según el texto: si es nulo (recupera todos),
     * si tiene una letra (LIKE 'X%'), o si tiene más de una letra (LIKE '%X%').
     * </p>
     *
     * @param texto El fragmento de texto a buscar en el nombre.
     * @return Una lista de objetos {@link Medicamento} coincidentes.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Medicamento> findByNombre(String texto) throws SQLException {
        List<Medicamento> medicamentos = new ArrayList<>();
        String sql;
        String param = null;
        boolean useParam = true;

        if (texto == null || texto.isEmpty() || "null".equalsIgnoreCase(texto)) {
            sql = "SELECT medicamento_id, nombre, descripcion FROM MEDICAMENTO ORDER BY nombre ASC";
            useParam = false;
        } else if (texto.length() <= 1) {
            sql = "SELECT medicamento_id, nombre, descripcion FROM MEDICAMENTO WHERE nombre LIKE ? ORDER BY nombre ASC";
            param = texto + "%";
        } else {
            sql = "SELECT medicamento_id, nombre, descripcion FROM MEDICAMENTO WHERE nombre LIKE ? ORDER BY nombre ASC";
            param = "%" + texto + "%";
        }

        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (useParam)
                statement.setString(1, param);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Medicamento medicamento = new Medicamento();
                    medicamento.setIdMedicamento(resultSet.getInt("medicamento_id"));
                    medicamento.setNombre(resultSet.getString("nombre"));
                    medicamento.setDescripcion(resultSet.getString("descripcion"));
                    medicamentos.add(medicamento);
                }
            }
        }
        return medicamentos;
    }
}