package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de repositorio que maneja las operaciones de persistencia (CRUD) para la entidad {@link Consulta}.
 *
 * @author VaquitaSoft
 * @version 1.0
 * @since 2025-10-19
 */
public class ConsultaRepository {

    /**
     * Persiste una nueva consulta médica en la tabla CONSULTA.
     * <p>
     * Se utiliza principalmente como parte del proceso de registro de una receta.
     * </p>
     *
     * @param consulta El objeto {@link Consulta} a guardar.
     * @return El ID (clave primaria) generado para la nueva consulta.
     * @throws SQLException Si ocurre un error de base de datos o si no se obtiene el ID generado.
     */
    public int save(Consulta consulta) throws SQLException{
        String sql ="INSERT INTO CONSULTA (arete_id, padecimiento) VALUES (?,?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, consulta.getGanado().getIdArete());
            statement.setString(2, consulta.getPadecimiento());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) return resultSet.getInt(1);
            throw new SQLException("No se generó la consulta.");
        }
    }

    /**
     * Recupera todas las consultas asociadas a un animal específico.
     *
     * @param areteId El ID del arete del animal cuyas consultas se desean obtener.
     * @return Una lista de objetos {@link Consulta}.
     * @throws SQLException Si ocurre un error de base de datos.
     */
    public List<Consulta> findByConsulta (int areteId) throws SQLException{
        String sql = "SELECT c.consulta_id, " +
                "c.padecimiento, " +
                "a.arete_id AS arete, " +
                "a.nombre " +
                "FROM CONSULTA c " +
                "JOIN ANIMAL a " +
                "ON c.arete_id = a.arete_id " +
                "WHERE a.arete_id = ?";
        List<Consulta> consultas = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, areteId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Consulta consulta = new Consulta();
                    Animal animal = new Animal();
                    animal.setIdArete(resultSet.getInt("arete"));
                    animal.setNombre(resultSet.getString("nombre"));
                    consulta.setIdConsulta(resultSet.getInt("consulta_id"));
                    consulta.setPadecimiento(resultSet.getString("padecimiento"));
                    consulta.setGanado(animal);
                    consultas.add(consulta);
                }
            }
        }
        return consultas;
    }
}