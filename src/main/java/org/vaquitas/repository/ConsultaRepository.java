package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Animal;
import org.vaquitas.model.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    //AGREGAR CONUSLTA (POR MEDIO DE RECETA)
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

    public List<Consulta> findByConsulta (long areteId) throws SQLException{
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