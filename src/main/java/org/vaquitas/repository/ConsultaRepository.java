package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
import org.vaquitas.model.Consulta;

import java.sql.*;

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
}
