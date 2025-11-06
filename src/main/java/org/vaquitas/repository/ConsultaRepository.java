package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaRepository {

    public int save(Consulta consulta) throws SQLException{
        String sql ="INSERT INTO CONSULTA (arete_id, padecimiento) VALUES (?,?)";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, consulta.getIdArete());
            statement.setString(2, consulta.getPadecimiento());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) return resultSet.getInt(1);
            throw new SQLException("No se genero la consula");
        }
    }

    public List<Consulta> findAll() throws SQLException{
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM CONSULTA";
        try(Connection connection = DatabaseConfig.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                Consulta consulta = new Consulta();
                consulta.setIdConsulta(resultSet.getInt("consulta_id"));
                consulta.setIdArete(resultSet.getInt("arete_id"));
                consulta.setPadecimiento(resultSet.getString("padecimiento"));
                consultas.add(consulta);
            }
            return consultas;
        }
    }
}
