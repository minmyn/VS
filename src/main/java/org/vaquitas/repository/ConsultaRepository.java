package org.vaquitas.repository;

import org.vaquitas.config.DatabaseConfig;
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
            // CORRECCIÓN del mensaje de error
            throw new SQLException("No se generó la consulta.");
        }
    }

}